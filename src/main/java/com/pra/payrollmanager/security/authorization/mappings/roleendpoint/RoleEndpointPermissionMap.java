package com.pra.payrollmanager.security.authorization.mappings.roleendpoint;

import com.pra.payrollmanager.base.data.BaseMapDAO;

public class RoleEndpointPermissionMap extends BaseMapDAO<String, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2586602126892269691L;

	public RoleEndpointPermissionMap(String roleId, int endpointId) {
		super(roleId, endpointId);
	}

}
