package com.pra.payrollmanager.security.authentication.user;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.security.authorization.mappings.roleendpoint.RoleEndpointMapDAL;
import com.pra.payrollmanager.security.authorization.mappings.rolepermission.RolePermissionMapDAL;
import com.pra.payrollmanager.security.authorization.mappings.userrole.UserRoleMapDAL;

@Service
@CacheConfig(cacheNames = CacheNameStore.SECURITY_USER_PERMISSION_STORE)
public class SecurityUserPermissionService {

	@Autowired
	UserRoleMapDAL userRoleMapDAL;

	@Autowired
	RolePermissionMapDAL rolePermissionMapDAL;

	@Autowired
	RoleEndpointMapDAL roleEndpointPermissionMapDAL;

	@Cacheable
	public Set<Integer> loadPermissionsForUserId(String userId) {
		String[] companyUser = userId.split("-");
		String userName = companyUser[1];
		return userRoleMapDAL.getValuesForKey(userName).stream()
				.flatMap(roleId -> rolePermissionMapDAL.getValuesForKey(roleId).stream())
				.collect(Collectors.toSet());
	}

	@Cacheable
	public Set<Integer> loadEndpointPermissionsForUserId(String userId) {
		String[] companyUser = userId.split("-");
		String userName = companyUser[1];
		return userRoleMapDAL.getValuesForKey(userName).stream()
				.flatMap(roleId -> roleEndpointPermissionMapDAL.getValuesForKey(roleId).stream())
				.collect(Collectors.toSet());
	}

}
