package com.pra.payrollmanager.security.authorization.permission;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pra.payrollmanager.user.root.permissions.security.SecurityPermission;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

@Builder
public class DynamicSecurityPermission {

	@NonNull
	private final Map<String, Integer> allowedDynamicParts;
	@NonNull
	private final String groupPrefix;
	@NonNull
	private final String action;
	@Setter(AccessLevel.NONE)
	private String dynamicPart;

	private String getId() {
		return groupPrefix.concat("_" + dynamicPart).concat("__" + action);
	}

	public static DynamicSecurityPermission of(String groupPrefix, String action,
			Map<String, Integer> allowedDynamicParts) {
		return DynamicSecurityPermission.builder()
				.groupPrefix(groupPrefix)
				.action(action)
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
