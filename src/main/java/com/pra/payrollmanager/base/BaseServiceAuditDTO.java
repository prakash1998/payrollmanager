package com.pra.payrollmanager.base;

abstract public class BaseServiceAuditDTO<PK,
		DAO extends BaseAuditDAOWithDTO<PK, DTO>,
		DTO extends BaseAuditDTO<DAO>,
		DAL extends BaseDALWithCompanyPostfixWithAuditLog<PK, DAO>> extends BaseServiceDTO<PK, DAO, DTO, DAL> {

}
