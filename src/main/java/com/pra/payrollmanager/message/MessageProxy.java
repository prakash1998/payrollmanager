package com.pra.payrollmanager.message;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageProxy {

	@Builder.Default
	String companyId = "-";
	@NonNull
	Message message;
	@Builder.Default
	Set<String> targetedUserIds = new HashSet<>();
//	@Builder.Default
//	boolean ignoreTargeted = false;

	public boolean isPublic() {
		return targetedUserIds.isEmpty();
	}

	public static MessageProxy of(Message message) {
		return MessageProxy.builder()
				.message(message)
				.build();
	}

	public static MessageProxy of(Message message, Set<String> targetedUserIds) {
		return MessageProxy.builder()
				.message(message)
				.targetedUserIds(targetedUserIds)
				.build();
	}

//	public static MessageProxy of(Message message, Set<String> targetedUserIds, boolean ignoreTargeted) {
//		return MessageProxy.builder()
//				.message(message)
//				.targetedUserIds(targetedUserIds)
//				.ignoreTargeted(ignoreTargeted)
//				.build();
//	}
}
