package technology.rocketjump.cruzorder.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Player;
import technology.rocketjump.cruzorder.discord.DiscordAccessToken;

import java.util.Date;

@Service
public class JwtService {

	private final Algorithm algorithm;

	public JwtService(Environment env) {
		// Re-using discord client secret for secret key
		String secret = env.getProperty("spring.security.oauth2.client.registration.discord.client-secret");
		if (secret == null) {
			throw new IllegalArgumentException("spring.security.oauth2.client.registration.discord.client-secret must be set");
		}
		this.algorithm = Algorithm.HMAC256(secret);
	}

	public String create(DiscordAccessToken accessToken, Player player) {
		return JWT.create()
				.withExpiresAt(new Date(accessToken.getExpires_at() * 1000L))
				.withSubject(player.getPlayerId())
				.withClaim("username", player.getDiscordUsername())
				.withClaim("avatar", player.getDiscordAvatar())
				.withClaim("is_admin", player.getIsAdmin())
				.withClaim("access_token", accessToken.getAccess_token())
				.withClaim("refresh_token", accessToken.getRefresh_token())
				.sign(algorithm);
	}

	public PlayerLoginToken parse(String jsonWebToken) {
		DecodedJWT decoded = JWT.decode(jsonWebToken);
		PlayerLoginToken civBlitzToken = new PlayerLoginToken();
		civBlitzToken.setDiscordId(decoded.getSubject());
		civBlitzToken.setDiscordUsername(decoded.getClaim("username").asString());
		civBlitzToken.setDiscordAvatar(decoded.getClaim("avatar").asString());
		civBlitzToken.setDiscordAccessToken(decoded.getClaim("access_token").asString());
		civBlitzToken.setDiscordRefreshToken(decoded.getClaim("refresh_token").asString());
		return civBlitzToken;
	}

}
