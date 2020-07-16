package com.pra.payrollmanager.security.authentication.user;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.admin.common.roles.RoleDAL;
import com.pra.payrollmanager.admin.common.roles.RoleDAO;
import com.pra.payrollmanager.apputils.cachemanager.AppCacheService;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.security.authorization.mappings.roleendpoint.RoleEndpointMapDAL;
import com.pra.payrollmanager.security.authorization.mappings.rolepermission.RolePermissionMapDAL;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.RoleResourceMapDAL;
import com.pra.payrollmanager.security.authorization.mappings.userrole.UserRoleMapDAL;
import com.pra.payrollmanager.user.root.permissions.resource.ResourcePermissionDAL;

@Service
public class SecurityUserPermissionService {

	@Autowired
	AuthorityService authService;

	@Autowired
	UserRoleMapDAL userRoleMapDAL;

	@Autowired
	RolePermissionMapDAL rolePermissionMapDAL;

	@Autowired
	RoleEndpointMapDAL roleEndpointPermissionMapDAL;

	@Autowired
	RoleResourceMapDAL roleResourceMapDAL;

	@Autowired
	RoleDAL roleDAL;

	@Autowired
	ResourcePermissionDAL resourcePermisionDAL;

	@Autowired
	AppCacheService cacheService;

	public Set<Integer> loadPermissionsForUser() {

		if (authService.isSuperUser()) {
			return authService.getSecurityCompany().getPermissions();
		}

		return cacheService.cacheWithUserId(CacheNameStore.USER_PERMISSION_STORE, (key) -> {
			Set<String> userRoles = userRoleMapDAL.getValuesForKey(authService.getUserName());
			Map<String, Set<Integer>> rolePermissionMap = rolePermissionMapDAL.getValuesForKeys(userRoles);
			return userRoles.stream()
					.flatMap(roleId -> rolePermissionMap.getOrDefault(roleId, new HashSet<>()).stream())
					.collect(Collectors.toSet());
		});
	}

	public Set<String> loadEndpointsForUser() {
		if (authService.isSuperUser()) {
			return authService.getSecurityCompany().getEndpoints();
		}
		return cacheService.cacheWithUserId(CacheNameStore.USER_ENDPOINT_STORE, (key) -> {
			Set<String> userRoles = userRoleMapDAL.getValuesForKey(authService.getUserName());
			Map<String, Set<String>> roleEndpointMap = roleEndpointPermissionMapDAL.getValuesForKeys(userRoles);
			return userRoles.stream()
					.flatMap(roleId -> roleEndpointMap.getOrDefault(roleId, new HashSet<>()).stream())
					.collect(Collectors.toSet());
		});
	}

	public Set<ObjectId> loadResourcesForUser() {
		if (authService.isSuperUser()) {
			return resourcePermisionDAL._findAll().stream()
					.map(r -> r.getId()).collect(Collectors.toSet());
		}
		return cacheService.cacheWithUserId(CacheNameStore.USER_RESOURCE_STORE, (key) -> {
			Set<String> userRoles = userRoleMapDAL.getValuesForKey(authService.getUserName());
			Map<String, Set<ObjectId>> roleResourceMap = roleResourceMapDAL.getValuesForKeys(userRoles);
			return userRoles.stream()
					.flatMap(roleId -> roleResourceMap.getOrDefault(roleId, new HashSet<>()).stream())
					.collect(Collectors.toSet());
		});
	}

	public Set<String> loadScreensForUser() {
		if (authService.isSuperUser()) {
			return authService.getSecurityCompany().getScreenIds();
		}

		return cacheService.cacheWithUserId(CacheNameStore.USER_SCREEN_STORE, (key) -> {
			Set<String> userRoles = userRoleMapDAL.getValuesForKey(authService.getUserName());
			Map<String, Set<RoleDAO>> roleResourceMap = roleDAL.findByIds(userRoles).stream()
					.collect(Collectors.groupingBy(role -> role.getId(), Collectors.toSet()));
			return userRoles.stream()
					.flatMap(roleId -> roleResourceMap.getOrDefault("test", new HashSet<>()).stream()
							.flatMap(role -> role.getScreenIds().stream()))
					.collect(Collectors.toSet());
		});
	}

	public Set<String> loadUsersWithResource(ObjectId resourceId) {
		return cacheService.cacheWithUserId(CacheNameStore.USER_RESOURCE_STORE, (key) -> {
			Set<String> userRoles = roleResourceMapDAL.getKeysForValue(resourceId);
			Map<String, Set<String>> roleUsersMap = userRoleMapDAL.getKeysForValues(userRoles);
			return userRoles.stream()
					.flatMap(roleId -> roleUsersMap.getOrDefault(roleId, new HashSet<>()).stream())
					.map(userName -> UserIdConversionUtils.userIdFromUserName(authService, userName))
					.collect(Collectors.toSet());
		});
	}

}
