package com.pra.payrollmanager.user.accounting.order;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class OrderDAL extends AuditDAL<ObjectId, OrderDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.ACCOUNTING_ORDERS;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.HOTEL__ORDERS;
	}

}
