package com.pra.payrollmanager.admin.accounting.customer;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class CustomerDAL extends AuditDAL<String,CustomerDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.CUSTOMER;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.RESOURCE__PRODUCT;
	}

}
