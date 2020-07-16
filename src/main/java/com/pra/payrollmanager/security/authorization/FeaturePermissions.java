package com.pra.payrollmanager.security.authorization;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermissionDAL;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeaturePermissions {

	public static final FeaturePermission ADMIN__USERS = FeaturePermission.of(1, ApiFeatures.AUDIT_LOG,
			ApiFeatures.REALTIME);

	public static final FeaturePermission ROOT__COMPANY = FeaturePermission.of(2, ApiFeatures.AUDIT_LOG,
			ApiFeatures.REALTIME);

	public static final FeaturePermission PRODUCT__STOCKBOOK = FeaturePermission.all(3);

	public static final FeaturePermission USER__NOTIFICATIONS = FeaturePermission.of(4, ApiFeatures.AUDIT_LOG,
			ApiFeatures.REALTIME);

	public static final FeaturePermission HOTEL__TABLES = FeaturePermission.of(5, ApiFeatures.REALTIME);

	public static final FeaturePermission HOTEL__ORDERS = FeaturePermission.of(6, ApiFeatures.REALTIME);

	public static final FeaturePermission HOTEL__ORDER_ITEMS = FeaturePermission.of(7, ApiFeatures.REALTIME);

	public static final FeaturePermission BASE__FILES = FeaturePermission.of(8, ApiFeatures.AUDIT_LOG);

	public static final FeaturePermission RESOURCE__PRODUCT = FeaturePermission.of(9, ApiFeatures.AUDIT_LOG);

	public static final FeaturePermission ROOT__PERMISSIONS = FeaturePermission.of(10, ApiFeatures.ACCESS_CONTROL);

	public static void persistApiPermissionsIfNot(FeaturePermissionDAL dataAccess) {
		Map<Integer, FeaturePermission> allPermisssions = new HashMap<>();
		Field[] allFields = FeaturePermissions.class.getDeclaredFields();

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

		List<FeaturePermission> modifiedDbPermissions = dataAccess._findAll().stream()
				.map(p -> {
					if (allPermisssions.containsKey(p.getNumericId())) {
						FeaturePermission newP = allPermisssions.get(p.getNumericId());
						return p.setFeatures(newP.getFeatures());
					}
					return p;
				}).collect(Collectors.toList());

		Set<Integer> numericPermissionIdsInDB = modifiedDbPermissions.stream()
				.map(FeaturePermission::getNumericId)
				.collect(Collectors.toSet());

		List<FeaturePermission> newPermissionsToPersist = allPermisssions.keySet().stream()
				.filter(id -> !numericPermissionIdsInDB.contains(id))
				.map(allPermisssions::get)
				.collect(Collectors.toList());

		modifiedDbPermissions.addAll(newPermissionsToPersist);

		dataAccess.truncateTable();
		dataAccess._insert(modifiedDbPermissions);

	}
}
