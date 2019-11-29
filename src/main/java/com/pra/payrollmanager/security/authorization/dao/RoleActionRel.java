package com.pra.payrollmanager.security.authorization.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleActionRel {
	private Integer id;
	private SecurityRole role;
	private SecurityAction action;
}
