package com.pra.payrollmanager.admin.common.roles;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.apputils.async.AsyncWrapperService;
import com.pra.payrollmanager.apputils.cachemanager.AppCacheService;
import com.pra.payrollmanager.base.services.ServiceDTO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.security.authorization.mappings.roleendpoint.RoleEndpointMapDAL;
import com.pra.payrollmanager.security.authorization.mappings.rolepermission.RolePermissionMapDAL;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.RoleResourceMapDAL;

@Service
public class RoleService extends ServiceDTO<String, RoleDAO, RoleDTO, RoleDAL> {

	@Autowired
	private RolePermissionMapDAL rolePermissionMapDAL;

	@Autowired
	private RoleEndpointMapDAL roleEndpointMapDAL;

	@Autowired
	private RoleResourceMapDAL roleResourceMapDAL;

	@Autowired
	private AppCacheService cacheService;

	@Autowired
	private AsyncWrapperService async;

	@Override
	public RoleDTO postProcessSave(RoleDTO dtoToSave, RoleDAO objFromDB) {
		RoleDTO dto = super.postProcessSave(dtoToSave, objFromDB);
		dto.setPermissions(dtoToSave.getPermissions());
		dto.setEndpoints(dtoToSave.getEndpoints());
		dto.setResources(dtoToSave.getResources());
		return dto;
	}

	@Override
	public RoleDTO postProcessGet(RoleDAO obj) {
		RoleDTO dto = super.postProcessGet(obj);
		String roleId = dto.getRoleId();
		var t1 = async.async(() -> rolePermissionMapDAL.getValuesForKey(roleId));
		var t2 = async.async(() -> roleEndpointMapDAL.getValuesForKey(roleId));
		var t3 = async.async(() -> roleResourceMapDAL.getValuesForKey(roleId));
		dto.setPermissions(async.await(t1));
		dto.setEndpoints(async.await(t2));
		dto.setResources(async.await(t3));
		return dto;
	}

	@Override
	public List<RoleDTO> postProcessMultiGet(List<RoleDAO> objList) {

		var t1 = async.async(() -> rolePermissionMapDAL.getValuesForAllKeys());
		var t2 = async.async(() -> roleEndpointMapDAL.getValuesForAllKeys());
		var t3 = async.async(() -> roleResourceMapDAL.getValuesForAllKeys());

		var rolePermissionMap = async.await(t1);
		var roleEndpointMap = async.await(t2);
		var roleResourceMap = async.await(t3);

		return objList.stream().map(roleDao -> {
			RoleDTO role = toDTO(roleDao);
			String roleId = role.getRoleId();
			role.setPermissions(rolePermissionMap.getOrDefault(roleId, new HashSet<>()));
			role.setEndpoints(roleEndpointMap.getOrDefault(roleId, new HashSet<>()));
			role.setResources(roleResourceMap.getOrDefault(roleId, new HashSet<>()));
			return role;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public RoleDTO create(RoleDTO role) throws DuplicateDataEx, AnyThrowable {
		rolePermissionMapDAL.replaceEntries(role.getRoleId(), role.getPermissions());
		roleEndpointMapDAL.replaceEntries(role.getRoleId(), role.getEndpoints());
		roleResourceMapDAL.replaceEntries(role.getRoleId(), role.getResources());
		return super.create(role);
	}

	@Transactional
	@Override
	public RoleDTO update(RoleDTO role) throws DataNotFoundEx, AnyThrowable {
		clearCaches();

		rolePermissionMapDAL.replaceEntries(role.getRoleId(), role.getPermissions());
		roleEndpointMapDAL.replaceEntries(role.getRoleId(), role.getEndpoints());
		roleResourceMapDAL.replaceEntries(role.getRoleId(), role.getResources());
		return super.update(role);
	}

	@Override
	@Transactional
	public RoleDTO delete(RoleDTO role) throws DataNotFoundEx, AnyThrowable {
		clearCaches();

		rolePermissionMapDAL.deleteEntriesByKey(role.getRoleId());
		roleEndpointMapDAL.deleteEntriesByKey(role.getRoleId());
		roleResourceMapDAL.deleteEntriesByKey(role.getRoleId());
		return super.delete(role);
	}

	private void clearCaches() {
		cacheService.clearCaches(CacheNameStore.USER_PERMISSION_STORE,
				CacheNameStore.USER_ENDPOINT_STORE,
				CacheNameStore.USER_RESOURCE_STORE,
				CacheNameStore.RESOURCE_USER_STORE);
	}

}
