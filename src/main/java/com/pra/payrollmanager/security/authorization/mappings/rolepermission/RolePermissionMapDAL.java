package com.pra.payrollmanager.security.authorization.mappings.rolepermission;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.MapDALWithCompany;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class RolePermissionMapDAL extends MapDALWithCompany<String, Integer, RolePermissionMap> {

	@Override
	public EntityName entity() {
		return EntityName.ROLE_PERMISSION_MAP;
	}

	@Override
	public RolePermissionMap getInstance(String key, Integer value) {
		return new RolePermissionMap(key, value);
	}

}
