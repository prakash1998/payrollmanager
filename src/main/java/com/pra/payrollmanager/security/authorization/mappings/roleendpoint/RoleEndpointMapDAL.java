package com.pra.payrollmanager.security.authorization.mappings.roleendpoint;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.MapDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class RoleEndpointMapDAL extends MapDAL<String, String, RoleEndpointMap> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.ROLE_ENDPOINT_MAP;
	}

	@Override
	public RoleEndpointMap getInstance(String key, String value) {
		return new RoleEndpointMap(key, value);
	}

}
