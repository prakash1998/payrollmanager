package com.pra.payrollmanager.security.authorization.dao;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecurityRole {
	private String roleId;
    private Collection<SecurityRoleGroup> roleGroups;
}
