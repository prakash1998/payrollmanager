package com.pra.payrollmanager.utils.basetest;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.audit.BaseDALWithCompanyPrefixWithAuditLog;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class BaseTestDAL extends BaseDALWithCompanyPrefixWithAuditLog<Integer, BaseTestDAO> {

	@Override
	protected EntityName entity() {
		return EntityName.BASE_TEST;
	}

}
