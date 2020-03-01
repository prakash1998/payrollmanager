package com.pra.payrollmanager.admin.company;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.BaseDALWithCompanyPostfixWithAuditLog;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class CompanyDetailsDAL extends BaseDALWithCompanyPostfixWithAuditLog<String, CompanyDetailsDAO> {

	@Override
	protected EntityName entity() {
		return EntityName.COMPANY;
	}

	@Override
	public String tableName() {
		return this.entity().table();
	}

}
