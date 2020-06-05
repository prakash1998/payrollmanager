package com.pra.payrollmanager.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.message.MessageProxy;
import com.pra.payrollmanager.translation.JsonJacksonMapperService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebSocketMessageSendingService {

	@Autowired
	private JsonJacksonMapperService mapper;

	@Autowired
	private SimpMessagingTemplate messageTemplate;

	@Autowired
	private SimpUserRegistry simpUserRegistry;

	public void send(String topic, MessageProxy wsMessage) {
		log.debug("message sent to topic : " + topic);
		log.debug("message is : " + wsMessage);

		String messageString = mapper.objectToJson(wsMessage.getMessage());
		if (wsMessage.isPublic()
		// || wsMessage.isIgnoreTargeted()
		) {
			String compnayPrefix = wsMessage.getCompanyId() + "-";
			simpUserRegistry.getUsers()
					.forEach(simpUser -> {
						String userId = simpUser.getName();
						if (
						// !wsMessage.getTargetedUserIds().contains(userId) &&
						userId.startsWith(compnayPrefix))
							messageTemplate.convertAndSendToUser(userId, topic, messageString);
					});
		} else {
			wsMessage.getTargetedUserIds().forEach(userId -> {
				messageTemplate.convertAndSendToUser(userId, topic, messageString);
			});
		}
	}

}
