package com.pra.payrollmanager.websocket.security;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.pra.payrollmanager.security.authentication.jwt.JwtTokenService;

import io.jsonwebtoken.JwtException;

@Component
public class AppHandshakeInterceptor implements HandshakeInterceptor {
	public static final String JWT_TOKEN = "jwt";
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		Optional<String> queryParam = Optional.ofNullable(request.getURI().getQuery());

		Optional<String> token = queryParam.flatMap(param -> {
			String[] values = param.split("=");
			if (values.length != 2) {
				return Optional.<String>empty();
			}
			if (!values[0].equals(JWT_TOKEN)) {
				return Optional.<String>empty();
			}
			return Optional.of(values[1]);
		});

		if (token.isPresent()) {
			String jwtToken = token.get();
			jwtTokenService.validateToken(jwtToken);
			attributes.put(JWT_TOKEN, jwtToken);
		} else {
			throw new JwtException("JWT Token is not provided.");
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
//		System.out.println("after handshake " + exception);
	}

}
