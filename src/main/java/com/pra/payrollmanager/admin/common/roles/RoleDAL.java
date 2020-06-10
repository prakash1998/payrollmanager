package com.pra.payrollmanager.admin.common.roles;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class RoleDAL extends AbstractDAL<String, RoleDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.ROLE;
	}

}
