package com.pra.payrollmanager.base.services;

import java.util.HashSet;
import java.util.Set;

import com.pra.payrollmanager.message.Message;
import com.pra.payrollmanager.message.MessageProxy;
import com.pra.payrollmanager.message.MessageSendingService;
import com.pra.payrollmanager.message.MessageType;
import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;

public interface BaseRTService<T extends Object> extends ApiRestriction {

	MessageSendingService messageService();

	String mqTopic();

	default Set<String> targetedUserIds(T obj) {
		return new HashSet<>();
	}

	default boolean realTimeDataEnabled() {
		return mqTopic() != null && isAllowedFor(ApiFeatures.REALTIME);
	}

	default void sendCreateMessage(T obj) {
		sendCreateMessage(obj, targetedUserIds(obj));
	}

	default void sendUpdateMessage(T obj) {
		sendUpdateMessage(obj, targetedUserIds(obj));
	}

	default void sendDeleteMessage(T obj) {
		sendDeleteMessage(obj, targetedUserIds(obj));
	}

	default void sendCreateMessage(T obj, Set<String> targetedUserIds) {
		sendMessage(obj, MessageType.INSERT, targetedUserIds);
	}

	default void sendUpdateMessage(T obj, Set<String> targetedUserIds) {
		sendMessage(obj, MessageType.UPDATE, targetedUserIds);
	}

	default void sendDeleteMessage(T obj, Set<String> targetedUserIds) {
		sendMessage(obj, MessageType.DELETE, targetedUserIds);
	}

	default T preprocessBeforeSend(T obj) {
		return obj;
	}

	default void sendMessage(T obj, MessageType type, Set<String> targetedUserIds) {
		send(
				Message.builder()
						.messageType(type)
						.payload(preprocessBeforeSend(obj))
						.build(),
				targetedUserIds);
	}

//	default boolean ignoreTargetedUserIds() {
//		return false;
//	}
	
	default void send(Message message, Set<String> targetedUserIds) {
		send(MessageProxy.of(message, targetedUserIds));
	}

	// default void send(Message message) {
	// if (realTimeDataEnabled())
	// send(MessageProxy.of(message, targetedUserIds()));
	// }

	default void send(MessageProxy message) {
		if (realTimeDataEnabled())
			messageService().send(mqTopic(), message);
	}

}
