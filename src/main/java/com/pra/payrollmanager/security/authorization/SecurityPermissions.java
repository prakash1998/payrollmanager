package com.pra.payrollmanager.security.authorization;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.security.authorization.permission.DynamicSecurityPermission;
import com.pra.payrollmanager.security.authorization.permission.SecurityPermission;
import com.pra.payrollmanager.security.authorization.permission.SecurityPermissionDAL;

import lombok.extern.slf4j.Slf4j;

/**
 * this class will contain constants for all permissions which will be used for
 * authorization of user purpose : store of all security-action objects. can be
 * used as constants in validations and tests we can also group them here to
 * show in ui
 * 
 * <b>CAUTION</b> :: -- this class is open for extension and closed for
 * modification. you can remove things and add things, but can not modify
 * existing things
 * 
 * current max index = 6
 * 
 * @author prakash dudhat
 *
 */

@Slf4j
public class SecurityPermissions {

	public static final SecurityPermission USERS__VIEWER = SecurityPermission.of(1);

	public static final SecurityPermission USERS__MANAGER = SecurityPermission.of(2);

	public static final SecurityPermission SECURITY_PERMISSION__MANAGER = SecurityPermission.of(3);

	public static final SecurityPermission USER__PASSWORD_UPDATE = SecurityPermission.of(4);

	public static final SecurityPermission COMAPNY_DETAILS__VIEWER = SecurityPermission.of(5);

	public static final SecurityPermission COMAPNY_DETAILS__MANAGER = SecurityPermission.of(6);

	public static final SecurityPermission ROLES__VIEWER = SecurityPermission.of(7);

	public static final SecurityPermission ROLES__MANAGER = SecurityPermission.of(8);
	
	public static final SecurityPermission API_PERMISSION__MANAGER = SecurityPermission.of(9);

	private static DynamicSecurityPermission createDynamicPermission(String prefix, String postfix, List<String> ids,
			List<Integer> numericIds) {
		// example usage
		// public static final DynamicSecurityPermission TEST =
		// createDynamicPermission("pre", "post",
		// Arrays.asList("test1", "test2"), Arrays.asList(4, 5));
		Set<Integer> numericIdSet = new HashSet<>(numericIds);

		Map<String, Integer> permissionMap = new HashMap<>();
		if (ids.size() != numericIdSet.size()) {
			throw new RuntimeException(String.format(
					"mis-match in id and numericIds size for perfix - '%s' and postfix - '%s' ", prefix, postfix));
		}

		for (int i = 0; i < ids.size(); i++) {
			if (permissionMap.containsKey(ids.get(i))) {
				throw new RuntimeException(String.format(
						"mis-match in id and numericIds size for perfix - '%s' and postfix - '%s' for id - '%s' ",
						prefix, postfix, ids.get(i)));
			}
			permissionMap.put(ids.get(i), numericIds.get(i));
		}

		return DynamicSecurityPermission.of(prefix, postfix, permissionMap);
	}

	public static void persistPermissionsIfNot(SecurityPermissionDAL repo) {
		// Set<String> permissionIds = new HashSet<>();
		Map<Integer, SecurityPermission> allPermisssions = new HashMap<>();
		Field[] allFields = SecurityPermissions.class.getDeclaredFields();

		try {
			for (Field field : allFields) {
				if (field.getType() == SecurityPermission.class) {
					SecurityPermission permission = (SecurityPermission) field.get(null);
					// if (!permissionIds.add(permission.getId())) {
					// throw new RuntimeException(
					// String.format("id - '%s' is repeated for field %s , please resolve it",
					// permission.getId(), field.getName()));
					// } else
					if (allPermisssions.containsKey(permission.getNumericId())) {
						throw new RuntimeException(
								String.format("numericId - '%s' is repeated  for field %s , please resolve  it",
										permission.getNumericId(), field.getName()));
					} else {
						allPermisssions.put(permission.getNumericId(),
								SecurityPermission.of(permission.getNumericId(), field.getName()));
					}
				}
				if (field.getType() == DynamicSecurityPermission.class) {
					DynamicSecurityPermission dynamicPermission = (DynamicSecurityPermission) field.get(null);
					dynamicPermission.allPossiblePermissions().forEach(permission -> {
						// if (!permissionIds.add(permission.getId())) {
						// throw new RuntimeException(
						// String.format(
						// "id - '%s' is repeated in Dynamic permission for field %s , please resolve
						// it",
						// permission.getId(), field.getName()));
						// } else
						if (allPermisssions.containsKey(permission.getNumericId())) {
							throw new RuntimeException(
									String.format(
											"numericId - '%s' is repeated in Dynamic permission for field %s , please resolve  it",
											permission.getNumericId(), field.getName()));
						} else {
							allPermisssions.put(permission.getNumericId(), permission);
						}
					});
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error("Error While Persisting Security Permissions in Database");
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		Set<Integer> numericPermissionIdsInDB = repo.findAll().stream()
				.map(SecurityPermission::getNumericId)
				.collect(Collectors.toSet());

		List<SecurityPermission> permissionsToPersist = allPermisssions.keySet().stream()
				.filter(id -> !numericPermissionIdsInDB.contains(id))
				.map(allPermisssions::get)
				.collect(Collectors.toList());
		try {
			repo.createAll(permissionsToPersist);
		} catch (DuplicateDataEx e) {
			throw new RuntimeException("Problem while inseting permissions in DB");
		}
	}

	// public static void main(String[] args) throws IllegalArgumentException,
	// IllegalAccessException {
	// // persistPermissionsIfNot();
	// }
}
