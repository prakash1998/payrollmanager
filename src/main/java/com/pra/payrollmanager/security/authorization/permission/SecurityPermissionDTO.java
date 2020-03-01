package com.pra.payrollmanager.security.authorization.permission;

import javax.validation.constraints.NotNull;

import com.pra.payrollmanager.base.BaseAuditDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SecurityPermissionDTO extends BaseAuditDTO<SecurityPermission> {

	@NotNull
	private Integer numericId;
	@NotNull
	private String id;
	private String display;
	private String category;
	private String description;

	@Override
	public SecurityPermission toDAO() {
		return SecurityPermission.builder()
				.id(id)
				.numericId(numericId)
				.display(display)
				.category(category)
				.description(description)
				.build();
	}

}
