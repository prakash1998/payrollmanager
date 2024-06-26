package com.pra.payrollmanager.security.authorization.mappings.userrole;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.MapDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class UserRoleMapDAL
		extends MapDAL<String, String, UserRoleMap> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.USER_ROLE_MAP;
	}

	@Override
	public UserRoleMap getInstance(String key, String value) {
		return new UserRoleMap(key, value);
	}

}
