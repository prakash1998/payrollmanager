package com.pra.payrollmanager.user.root.permissions.endpoint;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCommon;
import com.pra.payrollmanager.entity.CommonEntityNames;

@Repository
public class EndpointPermissionDAL extends DALWithCommon<String, EndpointPermission> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.ENDPOINT_PERMISSION;
	}

}
