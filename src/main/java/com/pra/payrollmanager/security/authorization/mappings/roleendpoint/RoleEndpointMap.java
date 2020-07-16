package com.pra.payrollmanager.security.authorization.mappings.roleendpoint;

import org.springframework.data.annotation.TypeAlias;

import com.pra.payrollmanager.base.data.BaseMapDAO;
import com.pra.payrollmanager.security.authorization.mappings.userrole.UserRoleMap;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeAlias("m")
public class RoleEndpointMap extends BaseMapDAO<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2586602126892269691L;
	

	public RoleEndpointMap(String key, String value) {
		super(key, value);
	}

}
