package technology.rocketjump.cruzorder.players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technology.rocketjump.cruzorder.auth.PlayerLoginToken;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Player;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

	private final PlayerRepo playerRepo;

	@Autowired
	public PlayerService(PlayerRepo playerRepo) {
		this.playerRepo = playerRepo;
	}

	public Player getPlayer(PlayerLoginToken token) {
		return getPlayer(token.getDiscordId(), token.getDiscordUsername(), token.getDiscordAvatar());
	}

	public synchronized Player getPlayer(String discordId, String discordUsername, String discordAvatar) {
		Optional<Player> existingPlayer = playerRepo.getPlayerByDiscordId(discordId);
		if (existingPlayer.isPresent()) {
			if (existingPlayer.get().getDiscordAvatar() == null && discordAvatar != null) {
				playerRepo.updateAvatar(discordId, discordAvatar);
				existingPlayer.get().setDiscordAvatar(discordAvatar);
			}
			return existingPlayer.get();
		} else {
			Player player = playerRepo.createPlayer(discordId, discordUsername, discordAvatar);
//			collectionService.initialiseCollection(player, 0);
			return player;
		}
	}

	public Optional<Player> getPlayerById(String playerId) {
		return playerRepo.getPlayerByDiscordId(playerId);
	}

}
