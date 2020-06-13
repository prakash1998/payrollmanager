package com.pra.payrollmanager.security.authentication.company;

import java.util.function.Predicate;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.RestrictedAuditDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.security.authorization.ResourceFeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class SecurityCompanyDAL extends RestrictedAuditDAL<String, SecurityCompany> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.SECURITY_COMPANY;
	}

	@Override
	public FeaturePermission apiPermission() {
		return ResourceFeaturePermissions.ROOT__COMPANY;
	}
	
	@Override
	public void validateModification(SecurityCompany dbObj, SecurityCompany objToSave) {
		
	}
	
	@Override
	public SecurityCompany injectAuditInfoOnCreate(SecurityCompany obj) {
		return obj;
	}
	
	@Override
	public SecurityCompany injectAuditInfoOnUpdate(SecurityCompany dbObj, SecurityCompany obj) {
		return obj;
	}
	
	@Override
	public Predicate<SecurityCompany> hasAccessToItem() {
		
		if(authorityService.inGodMode()) {
			return null;
		}
		
		String companyId = authorityService.getSecurityCompany().getId();
		return company -> company.getCreatedBy().startsWith(companyId+"-");
	}


}
