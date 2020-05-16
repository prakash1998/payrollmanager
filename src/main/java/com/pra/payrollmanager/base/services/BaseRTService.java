package com.pra.payrollmanager.base.services;

import java.util.HashSet;
import java.util.Set;

import com.pra.payrollmanager.message.Message;
import com.pra.payrollmanager.message.MessageSendingService;
import com.pra.payrollmanager.message.MessageUtil;
import com.pra.payrollmanager.message.MessageProxy;
import com.pra.payrollmanager.security.authorization.permission.ResourceFeatures;

public interface BaseRTService<PK> extends ApiRestriction {

	MessageSendingService messageService();

	String mqTopic();

	default Set<String> targetedUserIds() {
		return new HashSet<>();
	}

	default boolean realTimeDataEnabled() {
		return mqTopic() != null && isAllowedFor(ResourceFeatures.REALTIME);
	}

	default void sendCreateMessage(Object obj) {
		if (realTimeDataEnabled())
			send(MessageUtil.insertDataMessage(obj));
	}

	default void sendUpdateMessage(Object obj) {
		if (realTimeDataEnabled())
			send(MessageUtil.updateDataMessage(obj));
	}

	default void sendDeleteMessage(Object obj) {
		if (realTimeDataEnabled())
			send(MessageUtil.deleteDataMessage(obj));
	}

	default void send(Message message) {
		if (realTimeDataEnabled())
			send(MessageProxy.of(message, targetedUserIds()));
	}

	default void send(MessageProxy message) {
		if (realTimeDataEnabled())
			messageService().send(mqTopic(), message);
	}

}
