package com.pra.payrollmanager.security.authorization.permission;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.BaseAuditDAOWithDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class SecurityPermission extends BaseAuditDAOWithDTO<String, SecurityPermissionDTO> {

	@Id
	private String id;
	private int numericId;
	private String display;
	private String category;
	private String description;

	public static SecurityPermission of(int numericId, String id) {
		return SecurityPermission.builder()
				.id(id)
				.numericId(numericId)
				.display(id.replace("-", " "))
				.category(id)
				.description("-")
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
	public SecurityPermissionDTO toDto() {
		return SecurityPermissionDTO.builder()
				.id(id)
				.numericId(numericId)
				.display(display)
				.category(category)
				.description(description)
				.build();
	}
}
