package com.pra.payrollmanager.websocket.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.message.MessageProxy;
import com.pra.payrollmanager.websocket.WebSocketMessageSendingService;

@Service
public class HotelConsumers {

	@Autowired
	WebSocketMessageSendingService messageSenderService;

	@KafkaListener(topics = KafkaTopics.HOTEL_TABLES , autoStartup = "${spring.kafka.enabled}")
	public void sendToHotelTables(MessageProxy message) {
		messageSenderService.send(KafkaTopics.HOTEL_TABLES, message);
	}

	@KafkaListener(topics = KafkaTopics.HOTEL_ORDERS , autoStartup = "${spring.kafka.enabled}")
	public void sendToHotelOrders(MessageProxy message) {
		messageSenderService.send(KafkaTopics.HOTEL_ORDERS, message);
	}

	@KafkaListener(topics = KafkaTopics.HOTEL_ORDER_ITEMS , autoStartup = "${spring.kafka.enabled}")
	public void sendToHotelOrderDetail(MessageProxy message) {
		messageSenderService.send(KafkaTopics.HOTEL_ORDER_ITEMS, message);
	}

}
