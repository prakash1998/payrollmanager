package com.pra.payrollmanager.user.root.permissions.feature;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCommon;
import com.pra.payrollmanager.entity.CommonEntityNames;

@Repository
public class FeaturePermissionDAL extends DALWithCommon<String, FeaturePermission>{

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.FEATURE_PERMISSION;
	}

}
