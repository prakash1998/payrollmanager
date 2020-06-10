package com.pra.payrollmanager.user.root.permissions.feature;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;

@Repository
public class FeaturePermissionDAL extends AbstractDAL<String, FeaturePermission>{

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.FEATURE_PERMISSION;
	}

}
