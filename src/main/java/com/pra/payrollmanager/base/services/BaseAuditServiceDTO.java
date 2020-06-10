package com.pra.payrollmanager.base.services;

import java.util.List;
import java.util.stream.Collectors;

import com.pra.payrollmanager.base.dal.BaseAuditDAL;
import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;
import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.base.data.WithDTO;

public interface BaseAuditServiceDTO<PK,
		DAO extends BaseAuditDAOWithDTO<PK, DTO>,
		DTO extends BaseAuditDTO<DAO>,
		DAL extends BaseAuditDAL<PK, DAO>> {

	DAL dataAccessLayer();

	default List<DTO> getAllFromAudit() {
		return dataAccessLayer().findAllFromAudit().stream()
				.map(WithDTO::toDTO)
				.collect(Collectors.toList());
	}

}
