package com.pra.payrollmanager.base.services.next;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.base.dal.next.BaseAuditDAL;

abstract public class AuditRTServiceDAO<PK,
		DAO extends BaseAuditDAO<PK>,
		DAL extends BaseAuditDAL<PK, DAO>>
		extends RTServiceDAO<PK, DAO, DAL> {

}
