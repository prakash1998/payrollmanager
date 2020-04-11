package com.pra.payrollmanager.security.authentication.company;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.BaseDALWithCompanyPrefix;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class SecurityCompanyDAL extends BaseDALWithCompanyPrefix<String, SecurityCompany> {

	@Override
	protected EntityName entity() {
		return EntityName.SECURITY_COMPANY;
	}

	@Override
	public String tableName() {
		return this.entity().table();
	}

}
