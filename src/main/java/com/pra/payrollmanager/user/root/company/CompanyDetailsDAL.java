package com.pra.payrollmanager.user.root.company;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.security.authorization.ResourceFeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class CompanyDetailsDAL extends AuditDAL<String, CompanyDetailsDAO> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.COMPANY;
	}

	@Override
	public FeaturePermission apiPermission() {
		return ResourceFeaturePermissions.ROOT__COMPANY;
	}

//	@Override
//	public Criteria findAccessCriteria() {
//		return DBQueryUtils.startsWithCriteria("createdBy", authorityService.getSecurityCompany().getId() + "-");
//	}
//
//	@Override
//	public Predicate<CompanyDetailsDAO> hasAccessToItem() {
//		return company -> company.getCreatedBy().startsWith(authorityService.getSecurityCompany().getId()+"-");
//	}

}
