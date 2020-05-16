package com.pra.payrollmanager.security.authorization.mappings.rolepermission;

import com.pra.payrollmanager.base.data.BaseMapDAO;

public class RolePermissionMap extends BaseMapDAO<String, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2586602126892269691L;

	public RolePermissionMap(String roleId, Integer permissionId) {
		super(roleId, permissionId);
	}

}
