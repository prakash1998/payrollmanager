package com.pra.payrollmanager.security.authorization.dao;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecurityAction {
	private Integer id;
	private String name;
	private Set<RoleActionRel> roleActions = new HashSet<RoleActionRel>(0);
}
