package technology.rocketjump.cruzorder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technology.rocketjump.cruzorder.auth.JwtService;
import technology.rocketjump.cruzorder.auth.PlayerLoginToken;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Player;
import technology.rocketjump.cruzorder.players.PlayerService;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

	private final JwtService jwtService;
	private final PlayerService playerService;

	@Autowired
	public PlayerController(JwtService jwtService, PlayerService playerService) {
		this.jwtService = jwtService;
		this.playerService = playerService;
	}

	@GetMapping
	public ResponseEntity<Player> getLoggedInPlayer(@RequestHeader("Authorization") String jwToken) {
		if (jwToken == null) {
			return ResponseEntity.noContent().build();
		} else {
			PlayerLoginToken token = jwtService.parse(jwToken);
			Player player = playerService.getPlayer(token);
			return ResponseEntity.ok(player);
		}
	}

}
