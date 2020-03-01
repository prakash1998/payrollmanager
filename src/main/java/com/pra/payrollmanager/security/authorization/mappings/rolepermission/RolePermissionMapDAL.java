package com.pra.payrollmanager.security.authorization.mappings.rolepermission;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.BaseDALWithCompanyPostfix;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.utils.QueryUtils;

@Repository
public class RolePermissionMapDAL extends BaseDALWithCompanyPostfix<String, RolePermissionMap> {

	@Override
	protected EntityName entity() {
		return EntityName.USER_PERMISSION_MAP;
	}

	public Set<Integer> getPermissionsForRole(String roleId) {
		return super.findWith(QueryUtils.startsWith("_id", roleId))
				.stream().map(map -> map.getPermissionId())
				.collect(Collectors.toSet());
	}

	@Transactional
	public void replaceEntries(String roleId, Set<Integer> permissionIds) throws DuplicateDataEx {
		this.deleteEntriesByRoleId(roleId);
		List<RolePermissionMap> rolePermissionMaps = permissionIds.stream()
				.map(id -> RolePermissionMap.of(roleId, id))
				.collect(Collectors.toList());
		super.createAll(rolePermissionMaps);
	}

	public void deleteEntriesByRoleId(String roleId) {
		super.deleteWith(QueryUtils.startsWith("_id", roleId));
	}

	public void deleteEntriesByPermissionId(String permissionId) {
		super.deleteWith(QueryUtils.endsWith("_id", permissionId));
	}

}
