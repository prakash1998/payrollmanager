package com.pra.payrollmanager.base.services.next;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.base.dal.next.BaseAuditDAL;

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
