package com.pra.payrollmanager.security.authorization.mappings.userrole;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.dal.BaseDALWithCompanyPrefix;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.utils.QueryUtils;

@Repository
public class UserRoleMapDAL extends BaseDALWithCompanyPrefix<String, UserRoleMap> {

	@Override
	protected EntityName entity() {
		return EntityName.USER_ROLE_MAP;
	}

	public Set<String> getRoleIdsForUser(String userId) {
		return super.findWith(QueryUtils.startsWith("_id", userId))
				.stream().map(map -> map.getRoleId())
				.collect(Collectors.toSet());
	}

	@Transactional
	public void replaceEntries(String userId, Set<String> roleIds) throws DuplicateDataEx {
		this.deleteEntriesByUserId(userId);
		List<UserRoleMap> userRoleMaps = roleIds.stream()
				.map(id -> UserRoleMap.of(userId, id))
				.collect(Collectors.toList());
		super.createAll(userRoleMaps);
	}

	public void deleteEntriesByUserId(String userId) {
		super.deleteWith(QueryUtils.startsWith("_id", userId));
	}

	public void deleteEntriesByRoleId(String roleId) {
		super.deleteWith(QueryUtils.endsWith("_id", roleId));
	}

}
