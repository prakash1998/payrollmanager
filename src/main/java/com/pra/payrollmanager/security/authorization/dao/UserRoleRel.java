package com.pra.payrollmanager.security.authorization.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRoleRel {
	private Integer id;
	private SecurityUser user;
	private SecurityRole role;
}
