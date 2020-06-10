package com.pra.payrollmanager.security.authorization.mappings.roleendpoint;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.MapDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class RoleEndpointMapDAL extends MapDAL<String, Integer, RoleEndpointMap> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.ROLE_ENDPOINT_MAP;
	}

	@Override
	public RoleEndpointMap getInstance(String key, Integer value) {
		return new RoleEndpointMap(key, value);
	}

}
