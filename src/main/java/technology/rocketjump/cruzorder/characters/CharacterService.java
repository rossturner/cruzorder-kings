package technology.rocketjump.cruzorder.characters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Character;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Player;
import technology.rocketjump.cruzorder.model.rest.CharacterRequest;
import technology.rocketjump.cruzorder.model.rest.DecoratedCharacter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterService {

	private final CharacterRepo characterRepo;
	private final TerritoryRepo territoryRepo;

	@Autowired
	public CharacterService(CharacterRepo characterRepo, TerritoryRepo territoryRepo) {
		this.characterRepo = characterRepo;
		this.territoryRepo = territoryRepo;
	}

	public Optional<Character> getByDynastyName(String dynastyName) {
		return characterRepo.getCharacterByDynastyName(dynastyName);
	}

	public void createNew(Player player, CharacterRequest characterRequest) {
		BigDecimal dynastyId = characterRepo.nextDynastyIdentifier();

		territoryRepo.assignDynasty(dynastyId, characterRequest.getTerritoryId());
		characterRepo.createCharacter(dynastyId, player, characterRequest);

		for (String trait : characterRequest.getTraits()) {
			characterRepo.addTrait(dynastyId, trait);
		}

		for (int index = 0; index < characterRequest.getChildren().size(); index++) {
			characterRepo.createChild(dynastyId, characterRequest.getChildren().get(index), index);
		}
	}

	public void updateExisting(BigDecimal dynastyId, Player player, CharacterRequest characterRequest) {
		Optional<Character> existing = characterRepo.getCharacterByDynastyId(dynastyId);
		if (existing.isEmpty() || !existing.get().getPlayerId().equals(player.getPlayerId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		characterRepo.updateCharacter(dynastyId, player, characterRequest);

		characterRepo.deleteTraits(dynastyId);
		for (String trait : characterRequest.getTraits()) {
			characterRepo.addTrait(dynastyId, trait);
		}

		characterRepo.deleteChildren(dynastyId);
		for (int index = 0; index < characterRequest.getChildren().size(); index++) {
			characterRepo.createChild(dynastyId, characterRequest.getChildren().get(index), index);
		}
	}

	public List<DecoratedCharacter> getAllCharacters() {
		return characterRepo.getAllCharacters().stream().map(this::decorate).collect(Collectors.toList());
	}

	public List<DecoratedCharacter> getAllCharactersForPlayer(Player player) {
		return characterRepo.getAllCharactersForPlayer(player.getPlayerId()).stream().map(this::decorate).collect(Collectors.toList());
	}

	private DecoratedCharacter decorate(Character character) {
		DecoratedCharacter decorated = new DecoratedCharacter();
		decorated.setCharacter(character);
		decorated.setTerritory(territoryRepo.getTerritoryForDynasty(character.getBaseId()));
		return decorated;
	}
}
