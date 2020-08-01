package com.pra.payrollmanager.websocket.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.message.Message;
import com.pra.payrollmanager.message.MessageProxy;
import com.pra.payrollmanager.message.MessageType;
import com.pra.payrollmanager.websocket.config.WebSocketConfig;

import lombok.Data;

@Data
class TextContainer {
	private String data;
}

@Controller
public class StreamTestConsumer {

	@Value("${spring.kafka.enabled}")
	private boolean kafkaEnabled;

	@Autowired
	private SimpMessagingTemplate messageTemplate;

	@Autowired
	private KafkaTemplate<String, MessageProxy> kafkaTemplate;

	@MessageMapping("/" + KafkaTopics.LIVE_TEXT_EDITOR)
	public void liveText(TextContainer message) {
		// System.out.println(message);
		MessageProxy proxy = MessageProxy
				.of(Message.builder().messageType(MessageType.DELETE).payload(message).build());
		if (kafkaEnabled) {
			kafkaTemplate.send(KafkaTopics.LIVE_TEXT_EDITOR, proxy);
		} else {
			sendToTextEditor(proxy);
		}
	}

	@KafkaListener(topics = KafkaTopics.LIVE_TEXT_EDITOR, autoStartup = "${spring.kafka.enabled}")
	public void sendToTextEditor(MessageProxy message) {
		messageTemplate.convertAndSend(
				String.format("%s/%s", WebSocketConfig.TOPIC_PREFIX, KafkaTopics.LIVE_TEXT_EDITOR),
				message.getMessage().getPayload());
	}

}
