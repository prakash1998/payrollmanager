package com.pra.payrollmanager.base.services;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;
import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

abstract public class AuditRTServiceDTO<PK,
		DAO extends BaseAuditDAOWithDTO<PK, DTO>,
		DTO extends BaseAuditDTO<DAO>,
		DAL extends AuditDAL<PK, DAO>>
		extends RTServiceDTO<PK, DAO, DTO, DAL> implements BaseAuditServiceDTO<PK, DAO, DTO, DAL> {

	@Override
	public FeaturePermission apiPermission() {
		return dataAccessLayer.apiPermission();
	}

}