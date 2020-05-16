package com.pra.payrollmanager.user.root.permissions.feature;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCommon;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class FeaturePermissionDAL extends DALWithCommon<String, FeaturePermission>{

	@Override
	public EntityName entity() {
		return EntityName.FEATURE_PERMISSION;
	}

}
