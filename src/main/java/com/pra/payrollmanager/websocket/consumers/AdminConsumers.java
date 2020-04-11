package com.pra.payrollmanager.websocket.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.message.WSMessage;
import com.pra.payrollmanager.websocket.security.WebSocketMessageSendingService;

@Service
public class AdminConsumers {

	@Autowired
	WebSocketMessageSendingService messageSenderService;

	@KafkaListener(topics = KafkaTopics.USERS )
	public void consume(WSMessage message) {
		messageSenderService.send(KafkaTopics.USERS, message);
	}
}
