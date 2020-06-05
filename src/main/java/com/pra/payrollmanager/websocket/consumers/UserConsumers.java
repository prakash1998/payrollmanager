package com.pra.payrollmanager.websocket.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.message.MessageProxy;
import com.pra.payrollmanager.websocket.WebSocketMessageSendingService;

@Service
public class UserConsumers {

	@Autowired
	WebSocketMessageSendingService messageSenderService;

	@KafkaListener(topics = KafkaTopics.STOCK_BOOK , autoStartup = "${spring.kafka.enabled}")
	public void sendToStockBook(MessageProxy message) {
		messageSenderService.send(KafkaTopics.STOCK_BOOK, message);
	}
	
	@KafkaListener(topics = KafkaTopics.NOTIFICATIONS , autoStartup = "${spring.kafka.enabled}")
	public void sendToNotification(MessageProxy message) {
		messageSenderService.send(KafkaTopics.NOTIFICATIONS, message);
	}
}
