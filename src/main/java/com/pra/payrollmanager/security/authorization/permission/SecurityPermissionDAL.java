package com.pra.payrollmanager.security.authorization.permission;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.next.AuditDALWithoutCompany;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class SecurityPermissionDAL extends AuditDALWithoutCompany<String, SecurityPermission>{

	@Override
	public EntityName entity() {
		return EntityName.PERMISSION;
	}
	

}
