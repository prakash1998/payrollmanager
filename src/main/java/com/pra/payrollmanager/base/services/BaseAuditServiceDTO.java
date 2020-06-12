package com.pra.payrollmanager.base.services;

import java.util.List;
import java.util.stream.Collectors;

import com.pra.payrollmanager.base.dal.BaseAuditDAL;
import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.base.data.BaseAuditDTO;

public interface BaseAuditServiceDTO<PK,
		DAO extends BaseAuditDAO<PK>,
		DTO extends BaseAuditDTO<DAO>,
		DAL extends BaseAuditDAL<PK, DAO>> extends BaseServiceDTO<PK, DAO, DTO, DAL> {

	DAL dataAccessLayer();

	default List<DTO> getAllFromAudit() {
		return dataAccessLayer().findAllFromAudit().stream()
				.map(item -> toDTO(item))
				.collect(Collectors.toList());
	}

}
