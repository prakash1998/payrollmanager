package com.pra.payrollmanager.security.authentication.company;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class SecurityCompanyDAL extends AuditDAL<String, SecurityCompany> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.SECURITY_COMPANY;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.ROOT__COMPANY;
	}
	
	@Override
	public boolean modificationValid(SecurityCompany dbObj, SecurityCompany objToSave) {
		return true;
	}

}
