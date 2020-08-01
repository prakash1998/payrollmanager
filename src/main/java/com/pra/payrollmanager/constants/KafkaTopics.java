package com.pra.payrollmanager.constants;

import com.pra.payrollmanager.config.kafka.KafkaTopicConfig;

public class KafkaTopics {
	
	public static final String CACHE_SYNC = KafkaTopicConfig.TOPIC_PREFIX + "cache-sync";

//	public static final String USERS = KafkaTopicConfig.TOPIC_PREFIX + "users";

	public static final String STOCK_BOOK = KafkaTopicConfig.TOPIC_PREFIX + "stock-book";

	public static final String NOTIFICATIONS = KafkaTopicConfig.TOPIC_PREFIX + "notifications";

	public static final String HOTEL_TABLES = KafkaTopicConfig.TOPIC_PREFIX + "hotel-tables";

	public static final String HOTEL_ORDERS = KafkaTopicConfig.TOPIC_PREFIX + "hotel-orders";

	public static final String HOTEL_ORDER_ITEMS = KafkaTopicConfig.TOPIC_PREFIX + "hotel-order-items";

	public static final String LIVE_TEXT_EDITOR = KafkaTopicConfig.TOPIC_PREFIX + "live-text";
}
