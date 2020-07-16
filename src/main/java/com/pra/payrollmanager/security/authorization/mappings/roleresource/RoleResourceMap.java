package com.pra.payrollmanager.security.authorization.mappings.roleresource;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;

import com.pra.payrollmanager.base.data.BaseMapDAO;
import com.pra.payrollmanager.security.authorization.mappings.userrole.UserRoleMap;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeAlias("o")
public class RoleResourceMap extends BaseMapDAO<String, ObjectId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2586602126892269691L;

	public RoleResourceMap(String key, ObjectId value) {
		super(key, value);
	}

}
