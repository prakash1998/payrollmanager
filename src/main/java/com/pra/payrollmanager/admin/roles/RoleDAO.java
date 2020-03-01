package com.pra.payrollmanager.admin.roles;


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
public class RoleDAO extends BaseAuditDAOWithDTO<String, RoleDTO> {

	@Id
	String id;
	String name;

	@Override
	public String primaryKeyValue() {
		return id;
	}

	@Override
	public RoleDTO toDto() {
		return RoleDTO.builder()
				.roleId(id)
				.roleName(name)
				.build();
	}

}
