package technology.rocketjump.cruzorder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import technology.rocketjump.cruzorder.auth.JwtService;
import technology.rocketjump.cruzorder.auth.PlayerLoginToken;
import technology.rocketjump.cruzorder.characters.CharacterService;
import technology.rocketjump.cruzorder.characters.TerritoryRepo;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Character;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Player;
import technology.rocketjump.cruzorder.model.Trait;
import technology.rocketjump.cruzorder.model.rest.CharacterRequest;
import technology.rocketjump.cruzorder.model.rest.CharacterWithChildren;
import technology.rocketjump.cruzorder.model.rest.DecoratedCharacter;
import technology.rocketjump.cruzorder.players.PlayerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
public class CharactersController {

	private final JwtService jwtService;
	private final PlayerService playerService;
	private final CharacterService characterService;
	private final TerritoryRepo territoryRepo;

	@Autowired
	public CharactersController(JwtService jwtService, PlayerService playerService, CharacterService characterService, TerritoryRepo territoryRepo) {
		this.jwtService = jwtService;
		this.playerService = playerService;
		this.characterService = characterService;
		this.territoryRepo = territoryRepo;
	}

	@GetMapping
	public List<DecoratedCharacter> getAllCharacters(@RequestHeader("Authorization") String jwToken) {
		if (jwToken == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} else {
			PlayerLoginToken token = jwtService.parse(jwToken);
			Player player = playerService.getPlayer(token);

			if (player.getIsAdmin()) {
				return characterService.getAllCharacters();
			} else {
				return characterService.getAllCharactersForPlayer(player);
			}
		}
	}

	@GetMapping("/{dynastyId}")
	public CharacterWithChildren getCharacter(@RequestHeader("Authorization") String jwToken,
											  @PathVariable String dynastyId) {
		if (jwToken == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} else {
			PlayerLoginToken token = jwtService.parse(jwToken);
			Player player = playerService.getPlayer(token);

			Optional<Character> byDynastyId = characterService.getByDynastyId(dynastyId);
			if (byDynastyId.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			} else {
				if (player.getIsAdmin() || byDynastyId.get().getPlayerId().equals(player.getPlayerId())) {
					CharacterWithChildren response = new CharacterWithChildren();
					response.setCharacter(byDynastyId.get());
					response.setChildren(characterService.getChildren(byDynastyId.get().getBaseId()));
					response.setTraits(characterService.getTraits(byDynastyId.get().getBaseId()));
					response.setTerritoryId(territoryRepo.getTerritoryForDynasty(byDynastyId.get().getBaseId()).getTerritoryId());
					return response;
				} else {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN);
				}
			}
		}
	}

	@PostMapping
	@Transactional
	public void createNewCharacter(@RequestHeader("Authorization") String jwToken,
								   @RequestBody CharacterRequest characterRequest) {
		if (jwToken == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} else {
			PlayerLoginToken token = jwtService.parse(jwToken);
			Player player = playerService.getPlayer(token);

			if (!territoryRepo.isAvailable(characterRequest.getTerritoryId())) {
				throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
			}

			Optional<Character> existing = characterService.getByDynastyName(characterRequest.getDynastyName().trim());

			if (existing.isPresent()) {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			}
			if (totalCustomisationPoints(characterRequest) > 400) {
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
			}

			characterService.createNew(player, characterRequest);
		}
	}

	@PutMapping("/{dynastyId}")
	@Transactional
	public void updateExistingCharacter(@RequestHeader("Authorization") String jwToken,
										@RequestBody CharacterRequest characterRequest,
										@PathVariable String dynastyId) {
		if (jwToken == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} else {
			PlayerLoginToken token = jwtService.parse(jwToken);
			Player player = playerService.getPlayer(token);

			Optional<Character> existing = characterService.getByDynastyName(characterRequest.getDynastyName().trim());

			if (existing.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
			if (!existing.get().getPlayerId().equals(player.getPlayerId()) || !player.getIsAdmin()) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			}
			if (totalCustomisationPoints(characterRequest) > 400) {
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
			}

			characterService.updateExisting(existing.get().getBaseId(), player, characterRequest);
		}
	}

	private int totalCustomisationPoints(CharacterRequest characterRequest) {
		int customisationPoints = characterRequest.getTraits().stream()
				.map(t -> Trait.byInternalName.get(t).cost)
				.reduce(0, Integer::sum);
		customisationPoints += (characterRequest.getChildren().size() * 10);
		customisationPoints += skillCostCalculator(characterRequest.getBaseDiplomacy());
		customisationPoints += skillCostCalculator(characterRequest.getBaseStewardship());
		customisationPoints += skillCostCalculator(characterRequest.getBaseIntrigue());
		customisationPoints += skillCostCalculator(characterRequest.getBaseLearning());
		customisationPoints += skillCostCalculator(characterRequest.getBaseMartial());
		customisationPoints += skillCostCalculator(characterRequest.getBaseProwess());
		return customisationPoints;
	}

	private int skillCostCalculator(int skill) {
		int cost = 0;
		for (int cursor = 1; cursor <= skill; cursor++) {
			if (cursor <= 4) {
				cost += 2;
			} else if (cursor <= 8) {
				cost += 4;
			} else if (cursor <= 12) {
				cost += 7;
			} else if (cursor <= 16) {
				cost += 11;
			} else {
				cost += 17;
			}
		}
		return cost;
	}

}
