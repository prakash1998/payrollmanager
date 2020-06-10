package com.pra.payrollmanager.user.root.permissions.screen;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;

@Repository
public class ScreenPermissionDAL extends AbstractDAL<String, ScreenPermission> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.SCREEN_PERMISSION;
	}

}
