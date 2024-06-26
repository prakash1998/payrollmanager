package com.pra.payrollmanager.config.kafka;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.pra.payrollmanager.apputils.cachemanager.CacheSyncMsg;
import com.pra.payrollmanager.message.MessageProxy;
import com.pra.payrollmanager.translation.JsonJacksonMapperService;

@Configuration
@ConditionalOnProperty(
		value = "spring.kafka.enabled",
		havingValue = "true",
		matchIfMissing = false)
public class KafkaBaseConfig {

	@Autowired
	private JsonJacksonMapperService mapper;

	@Autowired
	private KafkaProperties kafkaProperties;

	// json factories
	private <T> ProducerFactory<String, T> jsonProducerFactory() {
		JsonSerializer<T> jsonSerializer = new JsonSerializer<>(mapper.mapper());
		return new DefaultKafkaProducerFactory<String, T>(kafkaProperties.buildProducerProperties(),
				new StringSerializer(), jsonSerializer);
	}

	private <T> ConsumerFactory<String, T> jsonConsumerFactory() {
		JsonDeserializer<T> jsonDeserializer = new JsonDeserializer<>(mapper.mapper());
		jsonDeserializer.addTrustedPackages("*");
		return new DefaultKafkaConsumerFactory<String, T>(kafkaProperties.buildConsumerProperties(),
				new StringDeserializer(), jsonDeserializer);
	}

	// MessageProxy obj config
	// json Producer configuration
	@Bean
	public ProducerFactory<String, MessageProxy> wsMessageProducerFactory() {
		return this.jsonProducerFactory();
	}

	@Bean
	public KafkaTemplate<String, MessageProxy> wsMessageKafakaTemplate() {
		return new KafkaTemplate<>(wsMessageProducerFactory());
	}

	// json Consumer configuration
	@Bean
	public ConsumerFactory<String, MessageProxy> wsMessageConsumerFactory() {
		return this.jsonConsumerFactory();
	}

	@Bean("kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, MessageProxy> wsMessageListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String,
				MessageProxy> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(wsMessageConsumerFactory());
		return factory;
	}

	// CachSyncMsg obj config
	// json Producer configuration
	@Bean
	public ProducerFactory<String, CacheSyncMsg> cacheSyncProducerFactory() {
		return this.jsonProducerFactory();
	}

	@Bean
	public KafkaTemplate<String, CacheSyncMsg> cacheSyncKafakaTemplate() {
		return new KafkaTemplate<>(cacheSyncProducerFactory());
	}

	// json Consumer configuration
	@Bean
	public ConsumerFactory<String, CacheSyncMsg> cacheSyncConsumerFactory() {
		return this.jsonConsumerFactory();
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CacheSyncMsg> cacheSyncListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String,
				CacheSyncMsg> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(cacheSyncConsumerFactory());
		return factory;
	}

	// Consumer configuration

	// If you only need one kind of deserialization , you only need to set the
	// Consumer configuration properties. Uncomment this and remove all others
	// below.
	// @Bean
	// public Map<String, Object> consumerConfigs() {
	// Map<String, Object> props = new HashMap<>(
	// kafkaProperties.buildConsumerProperties());
	// props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
	// StringDeserializer.class);
	// props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
	// StringDeserializer.class);
	//// props.put(ConsumerConfig.GROUP_ID_CONFIG,
	//// "default");
	//
	// return props;
	// }

	// // String Consumer Configuration
	//
	// @Bean
	// public Map<String, Object> producerConfigs() {
	// Map<String, Object> props = new
	// HashMap<>(kafkaProperties.buildProducerProperties());
	// props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	// StringSerializer.class);
	// props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
	// StringSerializer.class);
	// return props;
	//
	// }
	//
	// @Bean
	// public ProducerFactory<String, String> producerFactory() {
	// return new DefaultKafkaProducerFactory<>(producerConfigs());
	// }
	//
	// @Bean
	// public KafkaTemplate<String, String> kafkaTemplate() {
	// return new KafkaTemplate<>(producerFactory());
	// }
	//
	// @Bean
	// public ConsumerFactory<String, String> stringConsumerFactory() {
	// return new DefaultKafkaConsumerFactory<>(
	// kafkaProperties.buildConsumerProperties(), new StringDeserializer(), new
	// StringDeserializer());
	// }
	//
	// @Bean
	// public ConcurrentKafkaListenerContainerFactory<String, String>
	// kafkaListenerStringContainerFactory() {
	// ConcurrentKafkaListenerContainerFactory<String,
	// String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	// factory.setConsumerFactory(stringConsumerFactory());
	//
	// return factory;
	// }

	// // Byte Array Consumer Configuration
	//
	// @Bean
	// public ConsumerFactory<String, byte[]> byteArrayConsumerFactory() {
	// return new DefaultKafkaConsumerFactory<>(
	// kafkaProperties.buildConsumerProperties(), new StringDeserializer(), new
	// ByteArrayDeserializer()
	// );
	// }
	//
	// @Bean
	// public ConcurrentKafkaListenerContainerFactory<String, byte[]>
	// kafkaListenerByteArrayContainerFactory() {
	// ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
	// new ConcurrentKafkaListenerContainerFactory<>();
	// factory.setConsumerFactory(byteArrayConsumerFactory());
	// return factory;
	// }
}
