package com.pra.payrollmanager.base.services;

import com.pra.payrollmanager.base.dal.BaseAuditDAL;
import com.pra.payrollmanager.base.data.BaseAuditDAO;

abstract public class AuditServiceDAO<PK,
		DAO extends BaseAuditDAO<PK>,
		DAL extends BaseAuditDAL<PK, DAO>>
		extends ServiceDAO<PK, DAO, DAL> implements BaseAuditServiceDAO<PK, DAO, DAL> {

}
