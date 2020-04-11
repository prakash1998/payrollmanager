package com.pra.payrollmanager.security.authorization.permission;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.BaseAuditDAOWithDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class SecurityPermission extends BaseAuditDAOWithDTO<String, SecurityPermissionDTO> {

	@Id
	private String id;
	@NotNull
	private int numericId;
	private String display;
	private String category;
	private String description;

	public static SecurityPermission of(int numericId, String id) {
		String category = id.split("__")[0];
		return SecurityPermission.builder()
				.id(id)
				.numericId(numericId)
				.display(id.replace("__","_").replace("_", " "))
				.category(category)
				.description("-")
				.build();
	}

	public static SecurityPermission of(int numericId) {
		return SecurityPermission.builder()
				.numericId(numericId)
				.build();
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
