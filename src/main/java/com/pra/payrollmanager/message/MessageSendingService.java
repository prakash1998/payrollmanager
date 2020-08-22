package com.pra.payrollmanager.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.websocket.WebSocketMessageSendingService;

@Service
public class MessageSendingService {

	@Value("${spring.kafka.enabled}")
	private boolean kafkaEnabled;

	@Autowired(required = false)
	private KafkaTemplate<String, MessageProxy> messageTemplate;

	@Autowired
	WebSocketMessageSendingService localMessageService;

	@Autowired
	private AuthorityService authService;

	public void send(String topic, MessageProxy message) {
		message.setCompanyId(authService.getSecurityCompany().getId());
		if (kafkaEnabled) {
			messageTemplate.send(topic, message);
		} else {
			localMessageService.send(topic, message);
		}
	}

}
