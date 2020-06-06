package com.pra.payrollmanager.admin.common.roles;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCompany;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class RoleDAL extends DALWithCompany<String, RoleDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.ROLE;
	}

}
