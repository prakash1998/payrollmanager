package com.pra.payrollmanager.message;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
	
	@Builder.Default
	private UUID id = UUID.randomUUID();
	@NonNull
	private MessageType messageType;
	@NonNull
	private MessageOperation opType;
	private Object payload;

}
