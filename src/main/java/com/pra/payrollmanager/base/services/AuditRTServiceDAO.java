package com.pra.payrollmanager.base.services;

import com.pra.payrollmanager.base.dal.BaseAuditDAL;
import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

abstract public class AuditRTServiceDAO<PK,
		DAO extends BaseAuditDAO<PK>,
		DAL extends BaseAuditDAL<PK, DAO>>
		extends RTServiceDAO<PK, DAO, DAL> {

	@Override
	public FeaturePermission apiPermission() {
		return dataAccessLayer.apiPermission();
	}
	
}
