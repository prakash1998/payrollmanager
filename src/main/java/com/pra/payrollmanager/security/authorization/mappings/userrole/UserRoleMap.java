package com.pra.payrollmanager.security.authorization.mappings.userrole;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.BaseDAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserRoleMap implements BaseDAO<String> {

	@Id
	private String userRoleKey;

	@Getter
	private String userName;
	@Getter
	private String roleId;

	public static UserRoleMap of(String userName, String roleId) {
		String userRoleKey = String.format("%s``%s", userName, roleId);
		return new UserRoleMap(userRoleKey, userName, roleId);
	}

	@Override
	public String primaryKeyValue() {
		return userRoleKey;
	}

}
