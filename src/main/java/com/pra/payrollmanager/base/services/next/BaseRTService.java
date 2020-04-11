package com.pra.payrollmanager.base.services.next;

import java.util.HashSet;
import java.util.Set;

import com.pra.payrollmanager.message.Message;
import com.pra.payrollmanager.message.MessageSendingService;
import com.pra.payrollmanager.message.MessageUtil;
import com.pra.payrollmanager.message.WSMessage;

public interface BaseRTService<PK> {

	MessageSendingService messageService();

	String mqTopic();

	default Set<String> targetedUserIds() {
		return new HashSet<>();
	}

	default boolean realTimeDataEnabled() {
		return mqTopic() != null;
	}

	default void sendCreateMessage(Object obj) {
		if (realTimeDataEnabled())
			send(MessageUtil.insertDataMessage(obj));
	}

	default void sendUpdateMessage(Object obj) {
		if (realTimeDataEnabled())
			send(MessageUtil.updateDataMessage(obj));
	}

	default void sendDeleteMessage(PK key) {
		if (realTimeDataEnabled())
			send(MessageUtil.deleteDataMessage(key));
	}

	default void send(Message message) {
		if (realTimeDataEnabled())
			send(WSMessage.of(message, targetedUserIds()));
	}

	default void send(WSMessage message) {
		if (realTimeDataEnabled())
			messageService().send(mqTopic(), message);
	}

}
