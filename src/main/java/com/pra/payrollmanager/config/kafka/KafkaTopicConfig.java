package com.pra.payrollmanager.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pra.payrollmanager.constants.KafkaTopics;

@Configuration
public class KafkaTopicConfig {

	// private Map<String, NewTopic> topicStore = new HashMap<>();

	private NewTopic getTopic(String topicName) {
		// if (!topicStore.containsKey(topicName))
		// topicStore.put(topicName, new NewTopic(topicName, 3, (short) 1));
		// return topicStore.get(topicName);

		return new NewTopic(topicName, 3, (short) 1);
	}

	@Bean
	public NewTopic usersTopic() {
		return getTopic(KafkaTopics.USERS);
	}

	@Bean
	public NewTopic stockBookTopic() {
		return getTopic(KafkaTopics.STOCK_BOOK);
	}

	@Bean
	public NewTopic notificationsTopic() {
		return getTopic(KafkaTopics.NOTIFICATIONS);
	}
	
	@Bean
	public NewTopic hotelTablesTopic() {
		return getTopic(KafkaTopics.HOTEL_TABLES);
	}


}
