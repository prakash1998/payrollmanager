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
public class WSMessage {

	@Builder.Default
	String companyId = "-";
	@NonNull
	Message message;
	@Builder.Default
	Set<String> targetedUserIds = new HashSet<>();

	public boolean isPublic() {
		return targetedUserIds.isEmpty();
	}

	public static WSMessage of(Message message) {
		return WSMessage.builder()
				.message(message)
				.build();
	}

	public static WSMessage of(Message message, Set<String> targetedUserIds) {
		return WSMessage.builder()
				.message(message)
				.targetedUserIds(targetedUserIds)
				.build();
	}
}
