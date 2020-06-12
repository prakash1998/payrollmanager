package com.pra.payrollmanager.websocket.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.pra.payrollmanager.exception.unchecked.UnAuthorizedEx;
import com.pra.payrollmanager.websocket.config.WebSocketConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppInChannelInterceptor implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if (accessor.getCommand().equals(StompCommand.CONNECT)) {
			this.validateOnConnect(accessor);
		}
		if (accessor.getCommand().equals(StompCommand.SUBSCRIBE)) {
			this.validateOnSubscribe(accessor);
		}
		return message;
	}

	private void validateOnConnect(StompHeaderAccessor accessor) {
		log.debug("Validate condition on connect excecuted.");
	}

	private void validateOnSubscribe(StompHeaderAccessor accessor) {
		if (!accessor.getDestination()
				.startsWith(WebSocketConfig.DIRECT_USER_PREFIX + "/" + accessor.getUser().getName())) {
			throw new UnAuthorizedEx("Tried to access unathorized topic");
		}
		log.debug("Validate condition on connect excecuted.");
	}

}
