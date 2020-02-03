package com.pra.payrollmanager.security.authorization.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

@Builder
public class DynamicSecurityPermission {
	
	@NonNull
	private final Set<String> allowedDynamicParts;
	@NonNull
	private final String prefix;
	@NonNull
	private final String postfix;
	@Setter(AccessLevel.NONE)
	private String dynamicPart;
	
	private String getId() {
		return prefix.concat("-"+dynamicPart).concat("-"+postfix);
	}
	
	public static DynamicSecurityPermission of(String prefix,String postfix,List<String> allowedDynamicParts) {
		return DynamicSecurityPermission.builder()
				.prefix(prefix)
				.postfix(postfix)
				.allowedDynamicParts(new HashSet<>(allowedDynamicParts))
				.build() ;
	}
	
	private SecurityPermission toSecurityPermission() {
		return SecurityPermission.builder()
				.id(this.getId())
				.build();
	}
	
	public SecurityPermission withDynamicPart(String dynamicPart) {
		if(allowedDynamicParts.contains(dynamicPart))
			this.dynamicPart = dynamicPart;
		return this.toSecurityPermission();
	}
	
	public List<SecurityPermission> allPossiblePermissions(){
		return this.allowedDynamicParts.stream()
				.map( dynamicPart ->  this.withDynamicPart(dynamicPart))
				.collect(Collectors.toList());
	}
}
