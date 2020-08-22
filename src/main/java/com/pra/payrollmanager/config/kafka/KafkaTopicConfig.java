package com.pra.payrollmanager.config.kafka;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.pra.payrollmanager.constants.KafkaTopics;

@Configuration
@ConditionalOnProperty(
		value = "spring.kafka.enabled",
		havingValue = "true",
		matchIfMissing = false)
public class KafkaTopicConfig {

	public static final String TOPIC_PREFIX = "7s1lfhxl-";

	@Autowired
	private GenericWebApplicationContext context;

	@PostConstruct
	public void createTopics() {
		Arrays.stream(KafkaTopics.class.getDeclaredFields())
				.filter(field -> Modifier.isStatic(field.getModifiers()))
				.forEach(field -> {
					try {
						String topicName = field.get(null).toString();

						// creating bean with reflection
						// name : topicName
						// default partition : 3
						// default replicationFactor : 1
						context.registerBean(topicName, NewTopic.class, topicName, 5, (short) 1);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException("Error when creating kafka topics...", e);
					}
				});

	}

	// private NewTopic getTopic(String topicName) {
	// // if (!topicStore.containsKey(topicName))
	// // topicStore.put(topicName, new NewTopic(topicName, 3, (short) 1));
	// // return topicStore.get(topicName);
	//
	// return new NewTopic(topicName, 3, (short) 1);
	// }
	//
	// @Bean
	// public ApplicationRunner runner(KafkaAdmin kafkaAdmin) {
	// return args -> {
	// AdminClient admin = AdminClient.create(kafkaAdmin.getConfig());
	// List<NewTopic> topics = Arrays.stream(KafkaTopics.class.getDeclaredFields())
	// .filter(field -> Modifier.isStatic(field.getModifiers()))
	// .map(field -> {
	// try {
	// return getTopic(field.get(null).toString());
	// } catch (IllegalArgumentException | IllegalAccessException e) {
	// return null;
	// }
	// })
	// .filter(item -> item != null)
	// .collect(Collectors.toList());
	//
	// // build list
	// admin.createTopics(topics).all().get();
	//
	// };
	// }

	// @Bean
	// public NewTopic usersTopic() {
	// return getTopic(KafkaTopics.USERS);
	// }
	//
	// @Bean
	// public NewTopic stockBookTopic() {
	// return getTopic(KafkaTopics.STOCK_BOOK);
	// }
	//
	// @Bean
	// public NewTopic notificationsTopic() {
	// return getTopic(KafkaTopics.NOTIFICATIONS);
	// }
	//
	// @Bean
	// public NewTopic hotelTablesTopic() {
	// return getTopic(KafkaTopics.HOTEL_TABLES);
	// }
	//
	// @Bean
	// public NewTopic hotelOrdersTopic() {
	// return getTopic(KafkaTopics.HOTEL_ORDERS);
	// }
	//
	// @Bean
	// public NewTopic hotelOrderDetailTopic() {
	// return getTopic(KafkaTopics.HOTEL_ORDER_ITEMS);
	// }

}
