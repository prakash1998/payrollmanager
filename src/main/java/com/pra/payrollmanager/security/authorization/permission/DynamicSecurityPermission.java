package com.pra.payrollmanager.security.authorization.permission;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

@Builder
public class DynamicSecurityPermission {

	@NonNull
	private final Map<String, Integer> allowedDynamicParts;
	@NonNull
	private final String prefix;
	@NonNull
	private final String postfix;
	@Setter(AccessLevel.NONE)
	private String dynamicPart;

	private String getId() {
		return prefix.concat("-" + dynamicPart).concat("-" + postfix);
	}

	public static DynamicSecurityPermission of(String prefix, String postfix,
			Map<String, Integer> allowedDynamicParts) {
		return DynamicSecurityPermission.builder()
				.prefix(prefix)
				.postfix(postfix)
				.allowedDynamicParts(allowedDynamicParts)
				.build();
	}

	private SecurityPermission toSecurityPermission() {
		return SecurityPermission.builder()
				.id(this.getId())
				.numericId(allowedDynamicParts.get(this.dynamicPart))
				.build();
	}

	public SecurityPermission withDynamicPart(String dynamicPart) {
		if (allowedDynamicParts.containsKey(dynamicPart))
			this.dynamicPart = dynamicPart;
		return this.toSecurityPermission();
	}

	public List<SecurityPermission> allPossiblePermissions() {
		return this.allowedDynamicParts.keySet().stream()
				.map(id -> this.withDynamicPart(id))
				.collect(Collectors.toList());
	}
}
