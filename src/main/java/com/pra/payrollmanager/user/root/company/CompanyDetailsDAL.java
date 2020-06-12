package com.pra.payrollmanager.user.root.company;

import java.util.function.Predicate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.RestrictedAuditDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.security.authorization.ResourceFeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.utils.DBQueryUtils;

@Repository
public class CompanyDetailsDAL extends RestrictedAuditDAL<String, CompanyDetailsDAO> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.COMPANY;
	}

	@Override
	public FeaturePermission apiPermission() {
		return ResourceFeaturePermissions.ROOT__COMPANY;
	}

	@Override
	public Criteria findAllAccessCriteria() {
		return DBQueryUtils.startsWithCriteria("createdBy", authorityService.getSecurityCompany().getId() + "-");
	}

	@Override
	public Predicate<CompanyDetailsDAO> hasAccessToItem() {
		String companyId = authorityService.getSecurityCompany().getId();
		return company -> company.getId().equals(companyId) || company.getCreatedBy().startsWith(companyId+"-");
	}

}
