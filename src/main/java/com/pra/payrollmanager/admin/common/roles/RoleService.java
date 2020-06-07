package com.pra.payrollmanager.admin.common.roles;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.services.ServiceDTO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.security.authorization.mappings.roleendpoint.RoleEndpointMapDAL;
import com.pra.payrollmanager.security.authorization.mappings.rolepermission.RolePermissionMapDAL;

@Service
public class RoleService extends ServiceDTO<String, RoleDAO, RoleDTO, RoleDAL> {

	@Autowired
	RolePermissionMapDAL rolePermissionMapDAL;

	@Autowired
	RoleEndpointMapDAL roleEndpointPermissionMapDAL;

	private RoleDTO injectPermissions(RoleDTO dto) {
		dto.setPermissions(rolePermissionMapDAL.getValuesForKey(dto.getRoleId()));
		dto.setEndpoints(roleEndpointPermissionMapDAL.getValuesForKey(dto.getRoleId()));
		return dto;
	}

	private RoleDTO injectPermissionsFromTo(RoleDTO from, RoleDTO to) {
		to.setPermissions(from.getPermissions());
		to.setEndpoints(from.getEndpoints());
		return to;
	}

	@Override
	public List<RoleDTO> getAll() {
		return super.getAll().stream()
				.map(dto -> injectPermissions(dto))
				.collect(Collectors.toList());
	}
	

	@Override
	public List<RoleDTO> getByIds(Set<String> roleIds) {
		return super.getByIds(roleIds).stream()
				.map(dto -> injectPermissions(dto))
				.collect(Collectors.toList());
	}


	@Override
	public RoleDTO getById(String roleId) throws DataNotFoundEx, AnyThrowable {
		RoleDTO dto = super.getById(roleId);
		return injectPermissions(dto);
	}

	@Override
	@Transactional
	public RoleDTO create(RoleDTO role) throws DuplicateDataEx, AnyThrowable {
		RoleDTO savedObj = super.create(role);
		rolePermissionMapDAL.replaceEntries(role.getRoleId(), role.getPermissions());
		roleEndpointPermissionMapDAL.replaceEntries(role.getRoleId(), role.getEndpoints());
		return injectPermissionsFromTo(role, savedObj);
	}

	@Transactional
	@CacheEvict(cacheNames = CacheNameStore.SECURITY_USER_PERMISSION_STORE, allEntries = true)
	@Override
	public RoleDTO update(RoleDTO role) throws DataNotFoundEx, AnyThrowable {
		RoleDTO updatedObj = super.update(role);
		try {
			rolePermissionMapDAL.replaceEntries(role.getRoleId(), role.getPermissions());
			roleEndpointPermissionMapDAL.replaceEntries(role.getRoleId(), role.getEndpoints());
		} catch (DuplicateDataEx e) {
			throw new AnyThrowable(e);
		}
		return injectPermissionsFromTo(role, updatedObj);
	}

	@Override
	@Transactional
	public RoleDTO delete(RoleDTO role) throws DataNotFoundEx, AnyThrowable {
		super.delete(role);
		rolePermissionMapDAL.deleteEntriesByKey(role.getRoleId());
		roleEndpointPermissionMapDAL.deleteEntriesByKey(role.getRoleId());
		return role;
	}

}
