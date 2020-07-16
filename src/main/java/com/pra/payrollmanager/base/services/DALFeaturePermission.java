package com.pra.payrollmanager.base.services;

import com.pra.payrollmanager.base.dal.BaseAuditDAL;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

public interface DALFeaturePermission<DAL extends BaseAuditDAL<?, ?>> extends ApiRestriction {

	DAL dataAccessLayer();

	@Override
	default FeaturePermission apiPermission() {
		return dataAccessLayer().apiPermission();
	}
}
