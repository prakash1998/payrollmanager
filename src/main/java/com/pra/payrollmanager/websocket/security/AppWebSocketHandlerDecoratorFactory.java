package com.pra.payrollmanager.websocket.security;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import com.pra.payrollmanager.security.authentication.jwt.JwtTokenService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {

	private ScheduledExecutorService scheduledExcecutor = Executors.newSingleThreadScheduledExecutor();

	@Autowired
	private JwtTokenService jwtTokenService;

	@Override
	public WebSocketHandler decorate(final WebSocketHandler handler) {
		return new WebSocketHandlerDecorator(handler) {
			@Override
			public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
				String jwtToken = session.getAttributes().get(AppHandshakeInterceptor.JWT_TOKEN).toString();
				Date date = jwtTokenService.getExpirationDateFromToken(jwtToken);
				long timeRemainingInExpiryInMillis = date.getTime() - new Date().getTime();
				log.info("Time Remaining in JWT expiration :: " + timeRemainingInExpiryInMillis / 1000 + " seconds.");
				scheduledExcecutor.schedule(() -> {
					if (session.isOpen()) {
						try {
							log.info("closing websocket session because jwt is expired.");
							session.close(CloseStatus.NORMAL);
						} catch (IOException e) {
							log.error("Error while try to closing websocket connection.", e);
						}
					}
				}, timeRemainingInExpiryInMillis, TimeUnit.MILLISECONDS);
				super.afterConnectionEstablished(session);
			}
		};
	}

}
