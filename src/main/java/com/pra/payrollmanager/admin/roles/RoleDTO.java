package com.pra.payrollmanager.admin.roles;

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
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends BaseAuditDTO<RoleDAO> {

	@NotNull
	String roleId;
	String roleName;
	Set<Integer> permissions;
	Set<String> users;

	@Override           
	public RoleDAO toDAO() {
		return RoleDAO.builder()
				.id(roleId)
				.name(roleName)
				.build();
	}

}
