package com.pra.payrollmanager.base.services;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;
import com.pra.payrollmanager.base.data.BaseAuditDTO;

abstract public class AuditRTServiceDTO<PK,
		DAO extends BaseAuditDAOWithDTO<PK, DTO>,
		DTO extends BaseAuditDTO<DAO>,
		DAL extends AuditDAL<PK, DAO>>
		extends RTServiceDTO<PK, DAO, DTO, DAL> implements BaseAuditServiceDTO<PK, DAO, DTO, DAL>, DALFeaturePermission<DAL> {


}
