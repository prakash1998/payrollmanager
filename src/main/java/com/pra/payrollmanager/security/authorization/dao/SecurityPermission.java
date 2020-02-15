package com.pra.payrollmanager.security.authorization.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import com.pra.payrollmanager.dao.base.BaseDAOWithDTO;
import com.pra.payrollmanager.security.authorization.dto.SecurityPermissionDTO;

import lombok.Builder;

@Builder
@Document("SECURITY_PERMISSIONS")
public class SecurityPermission implements BaseDAOWithDTO<String, SecurityPermissionDTO> {
	private final String id;
	private final String display;
	private final String category;
	private final String description;

	public static SecurityPermission of(String id) {
		return SecurityPermission.builder()
				.id(id)
				.display(id)
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
