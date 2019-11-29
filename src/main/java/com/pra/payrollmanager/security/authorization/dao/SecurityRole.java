package com.pra.payrollmanager.security.authorization.dao;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SecurityRole {
	private Integer roleId;
	private String roleName;
	private Set<UserRoleRel> userRoles = new HashSet<UserRoleRel>(0);
	private Set<RoleActionRel> roleActions = new HashSet<RoleActionRel>(0);
}
