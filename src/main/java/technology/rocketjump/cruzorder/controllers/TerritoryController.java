package technology.rocketjump.cruzorder.controllers;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import technology.rocketjump.cruzorder.auth.JwtService;
import technology.rocketjump.cruzorder.auth.PlayerLoginToken;
import technology.rocketjump.cruzorder.characters.TerritoryRepo;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Player;
import technology.rocketjump.cruzorder.codegen.tables.pojos.TerritorySelection;
import technology.rocketjump.cruzorder.players.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/api/territory")
public class TerritoryController {

	private final DSLContext create;
	private final JwtService jwtService;
	private final PlayerService playerService;
	private final TerritoryRepo territoryRepo;

	@Autowired
	public TerritoryController(DSLContext create, JwtService jwtService, PlayerService playerService, TerritoryRepo territoryRepo) {
		this.create = create;
		this.jwtService = jwtService;
		this.playerService = playerService;
		this.territoryRepo = territoryRepo;
	}

	@GetMapping()
	public List<TerritorySelection> getAllTerritorySelections() {
			return territoryRepo.getAllTerritorySelections();
	}

	@GetMapping("/available")
	public List<TerritorySelection> getAvailableTerritory(@RequestHeader("Authorization") String jwToken) {
		if (jwToken == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} else {
			PlayerLoginToken token = jwtService.parse(jwToken);
			Player player = playerService.getPlayer(token);

			return territoryRepo.getAvailableTerritory(player.getIsAdmin());
		}
	}

}
