package com.pra.payrollmanager.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.security.authorization.AuthorityService;

@Service
public class MessageSendingService {

	@Autowired
	private KafkaTemplate<String, WSMessage> messageTemplate;

	@Autowired
	private AuthorityService authService;

	public void send(String topic, WSMessage message) {
		message.setCompanyId(authService.getSecurityCompany().getId());
		messageTemplate.send(topic, message);
	}

}
