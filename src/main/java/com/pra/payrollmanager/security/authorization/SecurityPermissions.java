package com.pra.payrollmanager.security.authorization;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.pra.payrollmanager.base.data.BulkOp;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.security.authorization.permission.DynamicSecurityPermission;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.user.root.permissions.security.SecurityPermission;
import com.pra.payrollmanager.user.root.permissions.security.SecurityPermissionDAL;

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
 * 
 * @author prakash dudhat
 *
 */

@Slf4j
public class SecurityPermissions {

	public static final Map<Integer, SecurityPermission> universalSecurityPermissionMap = new HashMap<>();

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

	public static void persistPermissionsIfNot(SecurityPermissionDAL dataAccess) {
		Map<Integer, SecurityPermission> allPermisssions = new HashMap<>();
		Field[] allFields = SecurityPermissions.class.getDeclaredFields();

		try {
			for (Field field : allFields) {
				if (field.getType() == SecurityPermission.class) {
					SecurityPermission permission = (SecurityPermission) field.get(null);
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

		List<SecurityPermission> updatedDbPermissions = dataAccess.findAll();
//				.stream()
//				.map(p -> {
//					if (allPermisssions.containsKey(p.getNumericId())) {
//						SecurityPermission newP = allPermisssions.get(p.getNumericId());
//						return p;
//					}
//					return p;
//				}).collect(Collectors.toList());

		Set<Integer> numericPermissionIdsInDB = updatedDbPermissions.stream()
				.map(SecurityPermission::getNumericId)
				.collect(Collectors.toSet());

		List<SecurityPermission> newlyCreatedPermissions = allPermisssions.keySet().stream()
				.filter(id -> !numericPermissionIdsInDB.contains(id))
				.map(allPermisssions::get)
				.collect(Collectors.toList());
		
		updatedDbPermissions.addAll(newlyCreatedPermissions);

		dataAccess.truncateTable();
		dataAccess.bulkOp(BulkOp.fromAdded(updatedDbPermissions));
		universalSecurityPermissionMap.putAll(allPermisssions);

	}

	// public static void main(String[] args) throws IllegalArgumentException,
	// IllegalAccessException {
	// // persistPermissionsIfNot();
	// }
}
