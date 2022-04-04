package technology.rocketjump.cruzorder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import technology.rocketjump.cruzorder.auth.AuditLogger;
import technology.rocketjump.cruzorder.auth.JwtService;
import technology.rocketjump.cruzorder.auth.PlayerLoginToken;
import technology.rocketjump.cruzorder.codegen.tables.pojos.AuditLog;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Player;
import technology.rocketjump.cruzorder.players.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final JwtService jwtService;
	private final PlayerService playerService;
	private final AuditLogger auditLogger;

	@Autowired
	public AdminController(JwtService jwtService, PlayerService playerService, AuditLogger auditLogger) {
		this.jwtService = jwtService;
		this.playerService = playerService;
		this.auditLogger = auditLogger;
	}

	@GetMapping("/audit_logs")
	public List<AuditLog> getAuditLogs(@RequestHeader("Authorization") String jwToken) {
		if (jwToken == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} else {
			PlayerLoginToken token = jwtService.parse(jwToken);
			Player player = playerService.getPlayer(token);
			if (!player.getIsAdmin()) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			} else {
				return auditLogger.getRecentLogs();
			}
		}
	}

}
