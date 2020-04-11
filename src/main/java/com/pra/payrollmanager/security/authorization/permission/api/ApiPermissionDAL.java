package com.pra.payrollmanager.security.authorization.permission.api;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.next.AuditDALWithoutCompany;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class ApiPermissionDAL extends AuditDALWithoutCompany<String, ApiPermission>{

	@Override
	public EntityName entity() {
		return EntityName.API_PERMISSION;
	}
	

}
