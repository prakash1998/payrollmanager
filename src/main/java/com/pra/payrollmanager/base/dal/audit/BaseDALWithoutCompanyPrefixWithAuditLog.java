package com.pra.payrollmanager.base.dal.audit;

import com.pra.payrollmanager.base.BaseAuditDAO;

abstract public class BaseDALWithoutCompanyPrefixWithAuditLog<PK,
		DAO extends BaseAuditDAO<PK>>
		extends BaseDALWithCompanyPrefixWithAuditLog<PK, DAO> {

	@Override
	public String tableName() {
		return this.entity().table();
	}

}
