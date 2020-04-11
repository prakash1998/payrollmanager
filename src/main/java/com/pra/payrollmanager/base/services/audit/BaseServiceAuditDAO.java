package com.pra.payrollmanager.base.services.audit;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.base.dal.audit.BaseDALWithCompanyPrefixWithAuditLog;
import com.pra.payrollmanager.base.services.BaseServiceDAO;

abstract public class BaseServiceAuditDAO<PK,
		DAO extends BaseAuditDAO<PK>,
		DAL extends BaseDALWithCompanyPrefixWithAuditLog<PK, DAO>> extends BaseServiceDAO<PK, DAO, DAL> {

	// CAUSION : don't write any logic here
	// this service is just to enforce developer to use
	// AuditDAO and AuditDAL
	// it does not have any logic for auditing
	// logic can be found in  respective dal -> audit
}
