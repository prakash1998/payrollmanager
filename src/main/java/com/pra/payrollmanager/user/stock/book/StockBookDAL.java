package com.pra.payrollmanager.user.stock.book;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class StockBookDAL extends AuditDAL<String, StockBookDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.STOCKBOOK;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.PRODUCT__STOCKBOOK;
	}

}
