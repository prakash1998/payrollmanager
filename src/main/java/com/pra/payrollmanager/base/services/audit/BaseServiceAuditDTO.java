package com.pra.payrollmanager.base.services.audit;

import com.pra.payrollmanager.base.BaseAuditDAOWithDTO;
import com.pra.payrollmanager.base.BaseAuditDTO;
import com.pra.payrollmanager.base.dal.audit.BaseDALWithCompanyPrefixWithAuditLog;
import com.pra.payrollmanager.base.services.BaseServiceDTO;

abstract public class BaseServiceAuditDTO<PK,
		DAO extends BaseAuditDAOWithDTO<PK, DTO>,
		DTO extends BaseAuditDTO<DAO>,
		DAL extends BaseDALWithCompanyPrefixWithAuditLog<PK, DAO>> extends BaseServiceDTO<PK, DAO, DTO, DAL> {

	// CAUSION : don't write any logic here
	// this service is just to enforce developer to use
	// AuditDAO and AuditDAL
	// it does not have any logic for auditing
	// logic can be found in  respective dal -> audit
}
