package com.pra.payrollmanager.base.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.dal.BaseAuditDAL;
import com.pra.payrollmanager.base.data.BaseAuditDAO;

abstract public class AuditServiceDAO<PK,
		DAO extends BaseAuditDAO<PK>,
		DAL extends BaseAuditDAL<PK, DAO>>
		implements NewBaseServiceDAO<PK, DAO, DAL> {

	@Autowired
	protected DAL dataAccessLayer;

	@Override
	public DAL dataAccessLayer() {
		return dataAccessLayer;
	}
}
