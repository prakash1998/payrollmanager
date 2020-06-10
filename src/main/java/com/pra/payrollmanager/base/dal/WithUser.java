package com.pra.payrollmanager.base.dal;

import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.entity.EntityName;
import com.pra.payrollmanager.security.authorization.AuthorityService;

public interface WithUser {

	EntityName entity();

	AuthorityService authorityService();

	default String user() {
		EntityName entity = entity();

		if (entity instanceof CompanyEntityNames) {
			return authorityService().getUserName();
		}
		
		return authorityService().getUserId();
	}
}
