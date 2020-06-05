package com.pra.payrollmanager.base.services;

import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

public interface ApiRestriction {

	AuthorityService authorityService();

	FeaturePermission apiPermission();

	default boolean isAllowedFor(ApiFeatures service) {
		if (authorityService().inGodMode())
			return true;
		return authorityService().getSecurityCompany()
				.hasAccessTo(apiPermission(), service);
	}

}
