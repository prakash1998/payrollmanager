package com.pra.payrollmanager.config.kafka;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

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

//	@Bean
//    public ApplicationRunner runner(KafkaAdmin kafkaAdmin) {
//        return args -> {
//            AdminClient admin = AdminClient.create(kafkaAdmin.getConfig());
//            List<NewTopic> topics = Arrays.stream(KafkaTopics.class.getDeclaredFields())
//            .filter(field -> Modifier.isStatic(field.getModifiers()))
//            .map( field -> {
//				try {
//					return getTopic(field.get(null).toString());
//				} catch (IllegalArgumentException | IllegalAccessException e) {
//					return null;
//				}
//			})
//            .filter(item -> item != null)
//            .collect(Collectors.toList());
//
//            // build list
//            admin.createTopics(topics).all().get();
//            
//        };
//    }

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

	@Bean
	public NewTopic hotelOrdersTopic() {
		return getTopic(KafkaTopics.HOTEL_ORDERS);
	}
	
	@Bean
	public NewTopic hotelOrderDetailTopic() {
		return getTopic(KafkaTopics.HOTEL_ORDER_DETAIL);
	}

}
