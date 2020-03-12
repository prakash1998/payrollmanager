package com.pra.payrollmanager.base;

abstract public class BaseServiceAuditDAO<PK,
		DAO extends BaseAuditDAO<PK>,
		DAL extends BaseDALWithCompanyPostfixWithAuditLog<PK, DAO>> extends BaseServiceDAO<PK, DAO, DAL> {

	
}
