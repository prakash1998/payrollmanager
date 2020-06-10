package com.pra.payrollmanager.user.root.permissions.endpoint;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;

@Repository
public class EndpointPermissionDAL extends AbstractDAL<String, EndpointPermission> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.ENDPOINT_PERMISSION;
	}

}
