package com.pra.payrollmanager.user.root.permissions.screen;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCommon;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class ScreenPermissionDAL extends DALWithCommon<String, ScreenPermission> {

	@Override
	public EntityName entity() {
		return EntityName.SCREEN_PERMISSION;
	}

}
