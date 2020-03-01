package com.pra.payrollmanager.utils.basetest;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.BaseDALWithCompanyPostfixWithAuditLog;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class BaseTestDAL extends BaseDALWithCompanyPostfixWithAuditLog<Integer, BaseTestDAO> {

	@Override
	protected EntityName entity() {
		return EntityName.BASE_TEST;
	}

}
