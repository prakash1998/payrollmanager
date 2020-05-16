package com.pra.payrollmanager.admin.common.roles;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCompany;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class RoleDAL extends DALWithCompany<String, RoleDAO> {

	@Override
	public EntityName entity() {
		return EntityName.ROLE;
	}

}
