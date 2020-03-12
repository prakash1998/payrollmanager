package com.pra.payrollmanager.security.authentication.company;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.BaseDALWithCompanyPostfix;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class SecurityCompanyDAL extends BaseDALWithCompanyPostfix<String, SecurityCompany> {

	@Override
	protected EntityName entity() {
		return EntityName.SECURITY_COMPANY;
	}

	@Override
	public String tableName() {
		return this.entity().table();
	}

}
