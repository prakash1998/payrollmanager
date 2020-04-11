package com.pra.payrollmanager.websocket.security;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.pra.payrollmanager.security.authentication.jwt.JwtTokenService;

/**
 * Assign a random username as principal for each websocket client. This is
 * needed to be able to communicate with a specific client.
 */
@Component
public class AppHandshakeHandler extends DefaultHandshakeHandler {

	@Autowired
	private JwtTokenService jwtTokenService;

	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		final String jwtToken = (String) attributes.get(AppHandshakeInterceptor.JWT_TOKEN);
		return new Principal() {
			@Override
			public String getName() {
				return jwtTokenService.getUserIdFromToken(jwtToken);
			}
		};
	}
}