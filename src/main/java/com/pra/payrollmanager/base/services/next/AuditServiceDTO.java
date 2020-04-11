package com.pra.payrollmanager.base.services.next;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.BaseAuditDAOWithDTO;
import com.pra.payrollmanager.base.BaseAuditDTO;
import com.pra.payrollmanager.base.dal.next.BaseAuditDAL;

abstract public class AuditServiceDTO<PK,
		DAO extends BaseAuditDAOWithDTO<PK, DTO>,
		DTO extends BaseAuditDTO<DAO>,
		DAL extends BaseAuditDAL<PK, DAO>>
		implements NewBaseServiceDTO<PK, DAO, DTO, DAL> {

	@Autowired
	protected DAL dataAccessLayer;

	@Override
	public DAL dataAccessLayer() {
		return dataAccessLayer;
	}

}
