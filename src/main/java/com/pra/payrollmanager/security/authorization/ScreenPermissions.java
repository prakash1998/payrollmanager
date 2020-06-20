package com.pra.payrollmanager.security.authorization;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.pra.payrollmanager.base.data.BulkOp;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.user.root.permissions.screen.ScreenPermission;
import com.pra.payrollmanager.user.root.permissions.screen.ScreenPermissionDAL;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScreenPermissions {

	public static final ScreenPermission USER_SCREEN = ScreenPermission.of();

	public static void persistScreenPermissionsIfNot(ScreenPermissionDAL dataAccess) {
		Map<String, ScreenPermission> allPermisssions = new HashMap<>();
		Field[] allFields = ScreenPermissions.class.getDeclaredFields();

		try {
			for (Field field : allFields) {
				if (field.getType() == ScreenPermission.class) {
					ScreenPermission permission = (ScreenPermission) field.get(null);
					if (allPermisssions.containsKey(field.getName())) {
						throw new RuntimeException(
								String.format("Id - '%s' is repeated  for field %s , please resolve  it",
										permission.getId(), field.getName()));
					} else {
						allPermisssions.put(field.getName(),
								ScreenPermission.of(field.getName()));
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error("Error While Persisting Security Permissions in Database");
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		Set<String> screenIdsInDb = dataAccess.findAll().stream()
				.map(ScreenPermission::getId)
				.collect(Collectors.toSet());

		List<ScreenPermission> permissionsToPersist = allPermisssions.keySet().stream()
				.filter(id -> !screenIdsInDb.contains(id))
				.map(allPermisssions::get)
				.collect(Collectors.toList());
		try {
			dataAccess.bulkOp(BulkOp.fromAdded(permissionsToPersist));
		} catch (DuplicateDataEx e) {
			throw new RuntimeException("Problem while inseting permissions in DB");
		}
	}

}
