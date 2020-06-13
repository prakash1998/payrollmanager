package com.pra.payrollmanager.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Message {
	
//	@Builder.Default
//	private UUID id = UUID.randomUUID();
	@NonNull
	private MessageType messageType;
//	@NonNull
//	private MessageOperation opType;
	private Object payload;

}
