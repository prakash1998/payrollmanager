package com.pra.payrollmanager.user.root.permissions.endpoint;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCommon;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class EndpointPermissionDAL extends DALWithCommon<String, EndpointPermission> {

	@Override
	public EntityName entity() {
		return EntityName.ENDPOINT_PERMISSION;
	}

}
