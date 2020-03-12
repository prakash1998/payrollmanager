package com.pra.payrollmanager.security.authorization.mappings.rolepermission;
	
import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.BaseDAO;

import lombok.Value;
import lombok.With;

@With
@Value
public class RolePermissionMap implements BaseDAO<String> {

	@Id
	private String rolePermissionId;

	private String roleId;
	private Integer permissionId;

	public static RolePermissionMap of(String roleId, Integer permissionId) {
		String rolePermiisionId = String.format("%s``%s", roleId, permissionId);
		return new RolePermissionMap(rolePermiisionId, roleId, permissionId);
	}

	@Override
	public String primaryKeyValue() {
		return roleId;
	}

}
