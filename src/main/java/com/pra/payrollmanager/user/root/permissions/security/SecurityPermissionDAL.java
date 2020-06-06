package com.pra.payrollmanager.user.root.permissions.security;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCommon;
import com.pra.payrollmanager.entity.CommonEntityNames;

@Repository
public class SecurityPermissionDAL extends DALWithCommon<String, SecurityPermission> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.PERMISSION;
	}

}
