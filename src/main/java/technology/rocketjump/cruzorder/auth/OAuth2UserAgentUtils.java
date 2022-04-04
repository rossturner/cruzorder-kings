package technology.rocketjump.cruzorder.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;

class OAuth2UserAgentUtils {

	private static final String DISCORD_BOT_USER_AGENT = "Cruzorder Kings (https://github.com/rossturner/cruzorder-kings)";

	static RequestEntity<?> withUserAgent(RequestEntity<?> request) {
		HttpHeaders headers = new HttpHeaders();
		headers.putAll(request.getHeaders());
		headers.add(HttpHeaders.USER_AGENT, DISCORD_BOT_USER_AGENT);

		return new RequestEntity<>(request.getBody(), headers, request.getMethod(), request.getUrl());
	}

}