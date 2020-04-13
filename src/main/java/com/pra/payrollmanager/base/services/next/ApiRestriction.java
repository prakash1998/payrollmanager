package com.pra.payrollmanager.base.services.next;

import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.security.authorization.permission.api.ApiPermission;
import com.pra.payrollmanager.security.authorization.permission.api.ApiServices;

public interface ApiRestriction {

	AuthorityService authorityService();

	ApiPermission apiPermission();

	default boolean isAllowedFor(ApiServices service) {
		if (authorityService().inGodMode())
			return true;
		return authorityService().getSecurityCompany()
				.hasAccessTo(apiPermission(), service);
	}

}
