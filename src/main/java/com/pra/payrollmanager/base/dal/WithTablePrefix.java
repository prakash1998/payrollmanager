package com.pra.payrollmanager.base.dal;

import com.pra.payrollmanager.security.authorization.AuthorityService;

public interface WithTablePrefix {
	
	default String commonPrefix() {
		return "COMMON_";
	}
	
	default String companyPrefix(AuthorityService authorityService) {
		return authorityService.getCompanyTablePrefix();
	}
	
	default String tablePrefix() {
		return "";
	}

}
