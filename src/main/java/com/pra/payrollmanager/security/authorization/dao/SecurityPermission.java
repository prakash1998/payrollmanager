package com.pra.payrollmanager.security.authorization.dao;

import com.pra.payrollmanager.dao.base.BaseDAOWithDTO;
import com.pra.payrollmanager.security.authorization.dto.SecurityPermissionDTO;

import lombok.Builder;

@Builder
public class SecurityPermission implements BaseDAOWithDTO<String, SecurityPermissionDTO> {
	private final String id;
	private final String display;
	private final String category;
	private final String description;

	public static SecurityPermission of(String id) {
		return SecurityPermission.builder()
				.id(id)
				.build();
	}

	public String getId() {
		return id;
	}

	@Override
	public String primaryKeyValue() {
		return id;
	}

	@Override
	public SecurityPermissionDTO toDTO() {
		return SecurityPermissionDTO.builder()
				.id(id)
				.display(display)
				.category(category)
				.description(description)
				.build();
	}

}
