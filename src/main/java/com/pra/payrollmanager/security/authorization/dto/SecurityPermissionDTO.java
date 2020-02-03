package com.pra.payrollmanager.security.authorization.dto;

import javax.validation.constraints.NotNull;

import com.pra.payrollmanager.dto.base.BaseDTO;
import com.pra.payrollmanager.security.authorization.dao.SecurityPermission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@ToString
public class SecurityPermissionDTO extends BaseDTO<SecurityPermission> {
	@NotNull
	private String id;
	private String display;
	private String category;
	private String description;

	@Override
	public SecurityPermission toDAO() {
		return SecurityPermission.builder()
				.id(id)
				.display(display)
				.category(category)
				.description(description)
				.build();
	}

}
