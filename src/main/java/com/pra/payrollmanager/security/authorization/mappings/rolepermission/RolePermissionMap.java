package com.pra.payrollmanager.security.authorization.mappings.rolepermission;

import org.springframework.data.annotation.TypeAlias;

import com.pra.payrollmanager.base.data.BaseMapDAO;
import com.pra.payrollmanager.security.authorization.mappings.userrole.UserRoleMap;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeAlias("n")
public class RolePermissionMap extends BaseMapDAO<String, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2586602126892269691L;

	public RolePermissionMap(String key, Integer value) {
		super(key, value);
	}

}
