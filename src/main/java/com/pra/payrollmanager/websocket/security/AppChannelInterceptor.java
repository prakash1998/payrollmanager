package com.pra.payrollmanager.websocket.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class AppChannelInterceptor implements ChannelInterceptor {

	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if (accessor.getCommand().equals(StompCommand.CONNECT)) {
			this.validateOnConnect(accessor);
		}
		return message;
	}

	private void validateOnConnect(StompHeaderAccessor accessor) {
		
	}

}
