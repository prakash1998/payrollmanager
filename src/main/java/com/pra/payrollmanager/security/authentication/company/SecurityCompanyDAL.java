package com.pra.payrollmanager.security.authentication.company;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;

@Repository
public class SecurityCompanyDAL extends AbstractDAL<String, SecurityCompany> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.SECURITY_COMPANY;
	}

}
