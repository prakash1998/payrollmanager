package com.pra.payrollmanager.base.services;

import java.util.List;

import com.pra.payrollmanager.base.dal.BaseAuditDAL;
import com.pra.payrollmanager.base.data.BaseAuditDAO;

public interface BaseAuditServiceDAO<PK, DAO extends BaseAuditDAO<PK>, DAL extends BaseAuditDAL<PK, DAO>> {

	DAL dataAccessLayer();

	default List<DAO> getAllFromAudit() {
		return dataAccessLayer().findAllFromAudit();
	}
}
