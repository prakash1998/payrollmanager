package com.pra.payrollmanager.websocket.security;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketEventListener {
//	@Autowired
//	private SimpMessageSendingOperations messagingTemplate;

	@EventListener
	public void handleWebSocketConnectedListener(SessionConnectedEvent event) {
		log.debug("Received a new web socket connection" + event);
	}

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectEvent event) {
		log.debug("Received a new web socket connection request" + event);
	}
	
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		log.debug("Dissconnected :: " + event);
	}
}