package com.pra.payrollmanager.user.root.company;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDALWithCommon;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.security.authorization.ResourceFeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class CompanyDetailsDAL extends AuditDALWithCommon<String, CompanyDetailsDAO> {

	@Override
	public EntityName entity() {
		return EntityName.COMPANY;
	}

	@Override
	public FeaturePermission apiPermission() {
		return ResourceFeaturePermissions.ROOT__COMPANY;
	}

}
