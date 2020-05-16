package com.pra.payrollmanager.security.authorization.mappings.userrole;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.MapDALWithCompany;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class UserRoleMapDAL
		extends MapDALWithCompany<String, String, UserRoleMap> {

	@Override
	public EntityName entity() {
		return EntityName.USER_ROLE_MAP;
	}

	@Override
	public UserRoleMap getInstance(String key, String value) {
		return new UserRoleMap(key, value);
	}

}
