package com.pra.payrollmanager.security.authorization.dao;

import java.util.Collection;

import com.pra.payrollmanager.security.authentication.dao.SecurityUser;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SecurityRoleGroup {
	private String roleGroupName;
    private Collection<SecurityUser> users;
    private Collection<SecurityRole> roles;   
}
