package com.pra.payrollmanager.security.authorization.permissions;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import com.pra.payrollmanager.security.authorization.dao.DynamicSecurityPermission;
import com.pra.payrollmanager.security.authorization.dao.SecurityPermission;
import com.pra.payrollmanager.security.authorization.repo.SecurityPermissionRepo;

import lombok.extern.slf4j.Slf4j;

/**
 * this class will contain constants for all permissions which will be used for
 * authorization of user purpose : store of all security-action objects. can be
 * used as constants in validations and tests we can also group them here to
 * show in ui
 * 
 * @author prakash dudhat
 *
 */

@Slf4j
public class SecurityPermissions {

	public static final SecurityPermission ADMIN = SecurityPermission.of("admin");

	public static final SecurityPermission USER = SecurityPermission.of("user");
	
	
	public static final SecurityPermission SECURITY_PERMISSION_MANAGER = SecurityPermission.of("security-permission-manager");
	
	public static final SecurityPermission PASSWORD_UPDATE = SecurityPermission.of("password-update");

	public static final DynamicSecurityPermission TEST = DynamicSecurityPermission.of("pre", "post",
			Arrays.asList("test1", "test2"));

	public static void persistPermissionsIfNot(SecurityPermissionRepo repo) {

		Set<String> permissionIds = new HashSet<>();

		Field[] allFields = SecurityPermissions.class.getDeclaredFields();

		try {
			for (Field field : allFields) {
				if (field.getType() == SecurityPermission.class) {
					SecurityPermission permission = (SecurityPermission) field.get(null);
					if (!permissionIds.add(permission.getId()))
						log.warn(permission.getId() + " <- is repeated and ignored for permission Name : "
								+ field.getName());
				}
				if (field.getType() == DynamicSecurityPermission.class) {
					DynamicSecurityPermission dynamicPermission = (DynamicSecurityPermission) field.get(null);
					dynamicPermission.allPossiblePermissions().forEach(permission -> {
						if (!permissionIds.add(permission.getId()))
							log.warn(permission.getId() + " <- is repeated and ignored for permission Name : "
									+ field.getName());
					});
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error("Error While Persisting Security Permissions in Database");
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		Set<String> permissionIdsInDB = repo.findAll().stream()
				.map(SecurityPermission::getId)
				.collect(Collectors.toSet());

		List<SecurityPermission> permissionsToPersist = permissionIds.stream()
				.filter(permission -> !permissionIdsInDB.contains(permission))
				.map(permissionId -> SecurityPermission.of(permissionId))
				.collect(Collectors.toList());
		repo.saveAll(permissionsToPersist);		
	}

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		// persistPermissionsIfNot();
	}
}
