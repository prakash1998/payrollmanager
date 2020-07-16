package com.pra.payrollmanager.security.authorization.mappings.userrole;

import org.springframework.data.annotation.TypeAlias;

import com.pra.payrollmanager.base.data.BaseMapDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeAlias("p")
public class UserRoleMap extends BaseMapDAO<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3985351081695565661L;

	public UserRoleMap(String key, String value) {
		super(key, value);
	}

}
