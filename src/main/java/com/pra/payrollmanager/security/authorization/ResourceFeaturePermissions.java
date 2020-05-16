package com.pra.payrollmanager.security.authorization;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermissionDAL;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceFeaturePermissions {

	public static final FeaturePermission ADMIN__USERS = FeaturePermission.of(1);
	
	public static final FeaturePermission ROOT__COMPANY = FeaturePermission.of(2);

	public static void persistApiPermissionsIfNot(FeaturePermissionDAL dataAccess) {
		Map<Integer, FeaturePermission> allPermisssions = new HashMap<>();
		Field[] allFields = ResourceFeaturePermissions.class.getDeclaredFields();

		try {
			for (Field field : allFields) {
				if (field.getType() == FeaturePermission.class) {
					FeaturePermission permission = (FeaturePermission) field.get(null);
					if (allPermisssions.containsKey(permission.getNumericId())) {
						throw new RuntimeException(
								String.format("numericId - '%s' is repeated  for field %s , please resolve  it",
										permission.getNumericId(), field.getName()));
					} else {
						allPermisssions.put(permission.getNumericId(),
								FeaturePermission.of(permission.getNumericId(), field.getName(),
										permission.getFeatures()));
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error("Error While Persisting Security Permissions in Database");
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		Set<Integer> numericPermissionIdsInDB = dataAccess.findAll().stream()
				.map(FeaturePermission::getNumericId)
				.collect(Collectors.toSet());

		List<FeaturePermission> permissionsToPersist = allPermisssions.keySet().stream()
				.filter(id -> !numericPermissionIdsInDB.contains(id))
				.map(allPermisssions::get)
				.collect(Collectors.toList());
		try {
			dataAccess.createMultiple(permissionsToPersist);
		} catch (DuplicateDataEx e) {
			throw new RuntimeException("Problem while inseting permissions in DB");
		}
	}
}
