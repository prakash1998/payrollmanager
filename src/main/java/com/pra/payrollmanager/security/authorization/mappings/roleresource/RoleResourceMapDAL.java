package com.pra.payrollmanager.security.authorization.mappings.roleresource;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.MapDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class RoleResourceMapDAL extends MapDAL<String, ObjectId, RoleResourceMap> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.ROLE_RESOURCE_MAP;
	}

	@Override
	public RoleResourceMap getInstance(String key, ObjectId value) {
		return new RoleResourceMap(key, value);
	}

}
