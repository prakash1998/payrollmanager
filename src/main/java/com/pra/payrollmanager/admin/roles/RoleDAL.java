package com.pra.payrollmanager.admin.roles;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.BaseDALWithCompanyPostfixWithAuditLog;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class RoleDAL extends BaseDALWithCompanyPostfixWithAuditLog<String , RoleDAO>{

	@Override
	protected EntityName entity() {
		return EntityName.ROLE;
	}

}
