package com.pra.payrollmanager.user.root.permissions.security;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCommon;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class SecurityPermissionDAL extends DALWithCommon<String, SecurityPermission> {

	@Override
	public EntityName entity() {
		return EntityName.PERMISSION;
	}

}
