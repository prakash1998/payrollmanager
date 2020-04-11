package com.pra.payrollmanager.base.dal;

import com.pra.payrollmanager.base.BaseDAO;

abstract public class BaseDALWithoutCompanyPrefix<PK, DAO extends BaseDAO<PK>>
		extends BaseDALWithCompanyPrefix<PK, DAO> {

	// CAUSION : don't write any logic here
	// this class is created, so that anyone do not miss to
	// specify table name if they want to prevent using
	// company prefix

	@Override
	public String tableName() {
		return this.entity().table();
	}

}
