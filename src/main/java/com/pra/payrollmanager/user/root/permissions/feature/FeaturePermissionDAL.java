package com.pra.payrollmanager.user.root.permissions.feature;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.base.dal.RestrictedDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.security.authentication.company.SecurityCompanyPermissionService;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.endpoint.EndpointPermission;
import com.pra.payrollmanager.utils.DBQueryUtils;

@Repository
public class FeaturePermissionDAL extends RestrictedDAL<String, FeaturePermission> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.FEATURE_PERMISSION;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.ROOT__PERMISSIONS;
	}

	@Autowired
	SecurityCompanyPermissionService companyPermissionService;

	@Override
	public Predicate<FeaturePermission> hasAccessToItem() {
		return item -> companyPermissionService.loadApiFeaturesForCompany()
				.contains(item.getNumericId());
	}

	@Override
	public Criteria findAllAccessCriteria() {
		return DBQueryUtils.inArrayCriteria("numericId",
				companyPermissionService.loadApiFeaturesForCompany().toArray());
	}

}
