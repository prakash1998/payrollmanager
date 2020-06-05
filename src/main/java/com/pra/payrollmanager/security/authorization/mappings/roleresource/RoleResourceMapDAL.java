package com.pra.payrollmanager.security.authorization.mappings.roleresource;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.MapDALWithCompany;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class RoleResourceMapDAL extends MapDALWithCompany<String, Integer, RoleResourceMap> {

	@Override
	public EntityName entity() {
		return EntityName.ROLE_PERMISSION_MAP;
	}

	@Override
	public RoleResourceMap getInstance(String key, Integer value) {
		return new RoleResourceMap(key, value);
	}

}
