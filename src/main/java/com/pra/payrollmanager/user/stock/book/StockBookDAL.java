package com.pra.payrollmanager.user.stock.book;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDALWithCommon;
import com.pra.payrollmanager.base.dal.AuditDALWithCompany;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.security.authorization.ResourceFeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class StockBookDAL extends AuditDALWithCompany<String, StockBookDAO> {

	@Override
	public EntityName entity() {
		return EntityName.STOCKBOOK;
	}

	@Override
	public FeaturePermission apiPermission() {
		return ResourceFeaturePermissions.PRODUCT__STOCKBOOK;
	}

}