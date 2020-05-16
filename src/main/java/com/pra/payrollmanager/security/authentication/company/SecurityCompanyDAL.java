package com.pra.payrollmanager.security.authentication.company;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCommon;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class SecurityCompanyDAL extends DALWithCommon<String, SecurityCompany> {

	@Override
	public EntityName entity() {
		return EntityName.SECURITY_COMPANY;
	}

}
