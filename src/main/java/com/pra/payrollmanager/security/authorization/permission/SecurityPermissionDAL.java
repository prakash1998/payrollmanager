package com.pra.payrollmanager.security.authorization.permission;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.BaseDALWithCompanyPostfix;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class SecurityPermissionDAL extends BaseDALWithCompanyPostfix<String, SecurityPermission>{

	@Override
	protected EntityName entity() {
		return EntityName.PERMISSION;
	}
	
	@Override
	public String tableName() {
		return this.entity().table();
	}

}
