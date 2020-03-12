package com.pra.payrollmanager.admin.roles;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.BaseServiceAuditDTO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.NotUseThisMethod;
import com.pra.payrollmanager.security.authorization.mappings.rolepermission.RolePermissionMapDAL;

@Service
public class RoleService extends BaseServiceAuditDTO<String, RoleDAO, RoleDTO, RoleDAL> {

	@Autowired
	RolePermissionMapDAL rolePermissionMapDAL;

	@Override
	public List<RoleDTO> getAllDtos() {
		return super.getAllDtos().stream()
				.map(dto -> {
					dto.setPermissions(rolePermissionMapDAL.getPermissionsForRole(dto.getRoleId()));
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public RoleDTO getDtoById(String roleId) throws DataNotFoundEx {
		RoleDTO dto = super.getDtoById(roleId);
		dto.setPermissions(rolePermissionMapDAL.getPermissionsForRole(dto.getRoleId()));
		return dto;
	}

	@Override
	@Transactional
	public void create(RoleDTO role) throws DuplicateDataEx {
		super.create(role);
		rolePermissionMapDAL.replaceEntries(role.roleId, role.permissions);
	}

	@Transactional
	@CacheEvict(cacheNames = CacheNameStore.SECURITY_USER_PERMISSION_STORE, allEntries = true)
	public void updateRole(RoleDTO role) throws DataNotFoundEx, DuplicateDataEx {
		super.update(role);
		rolePermissionMapDAL.replaceEntries(role.roleId, role.permissions);
	}

	@Override
	public void update(RoleDTO user) {
		throw new NotUseThisMethod();
	}

	@Override
	@Transactional
	public void delete(RoleDTO role) throws DataNotFoundEx {
		super.update(role);
		rolePermissionMapDAL.deleteEntriesByRoleId(role.roleId);
	}

}
