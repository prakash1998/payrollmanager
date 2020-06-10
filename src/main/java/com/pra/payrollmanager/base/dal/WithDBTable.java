package com.pra.payrollmanager.base.dal;

import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.entity.EntityName;
import com.pra.payrollmanager.security.authorization.AuthorityService;

public interface WithDBTable {

	EntityName entity();

	AuthorityService authorityService();
	
	default String companyTablePrefix() {
		return authorityService().getCompanyTablePrefix();
	}

	default String commonTablePrefix() {
		return "COMMON_";
	}

	default String tableName() {
		EntityName entity = entity();
		if (entity instanceof CompanyEntityNames) {
			return companyTablePrefix() + entity.table();
		}
		if (entity instanceof CommonEntityNames) {
			return commonTablePrefix() + entity.table();
		}
		return entity.table();
	};

}
