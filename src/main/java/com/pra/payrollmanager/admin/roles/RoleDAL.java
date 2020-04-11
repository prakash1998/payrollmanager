package com.pra.payrollmanager.admin.roles;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.audit.BaseDALWithCompanyPrefixWithAuditLog;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class RoleDAL extends BaseDALWithCompanyPrefixWithAuditLog<String , RoleDAO>{

	@Override
	protected EntityName entity() {
		return EntityName.ROLE;
	}

}
