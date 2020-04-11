package com.pra.payrollmanager.message;

public class MessageUtil {

	public static <T> Message insertDataMessage(T obj) {
		return Message.builder()
				.messageType(MessageType.DATA_MANIP)
				.opType(MessageOperation.INSERT)
				.payload(obj)
				.build();
	}

	public static <T> Message updateDataMessage(T obj) {
		return Message.builder()
				.messageType(MessageType.DATA_MANIP)
				.opType(MessageOperation.UPDATE)
				.payload(obj)
				.build();
	}

	public static <T> Message deleteDataMessage(T obj) {
		return Message.builder()
				.messageType(MessageType.DATA_MANIP)
				.opType(MessageOperation.DELETE)
				.payload(obj)
				.build();
	}
}
