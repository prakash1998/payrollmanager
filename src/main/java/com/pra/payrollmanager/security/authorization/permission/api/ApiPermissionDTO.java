package com.pra.payrollmanager.security.authorization.permission.api;

import java.util.HashSet;
import java.util.Set;

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
@EqualsAndHashCode(callSuper = false)
public class ApiPermissionDTO extends BaseAuditDTO<ApiPermission> {

	@NotNull
	private Integer numericId;
	@NotNull
	private String id;
	private String display;
	private String category;
	@Builder.Default
	private Set<ApiServices> services = new HashSet<>();

	@Override
	public ApiPermission toDAO() {
		return ApiPermission.builder()
				.id(id)
				.numericId(numericId)
				.display(display)
				.category(category)
				.services(services)
				.build();
	}

}
