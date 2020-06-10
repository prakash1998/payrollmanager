package com.pra.payrollmanager.user.root.permissions.security;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;

@Repository
public class SecurityPermissionDAL extends AbstractDAL<String, SecurityPermission> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.PERMISSION;
	}

}
