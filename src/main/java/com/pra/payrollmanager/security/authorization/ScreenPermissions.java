package com.pra.payrollmanager.security.authorization;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.pra.payrollmanager.user.root.permissions.screen.ScreenPermission;
import com.pra.payrollmanager.user.root.permissions.screen.ScreenPermissionDAL;

public class ScreenPermissions {

	private interface Screens {
		String name();
	}

	private enum UserScreens implements Screens {
		HOME, STOCK_BOOK, COMPANY, PERMISSION_CONFIG, FEATURE_CONFIG, SCREEN_CONFIG , ENDPOINT_CONFIG
	};

	private enum AdminScreens implements Screens {
		USERS, ROLES, PRODUCTS
	};

	private static void checkedInsertInSet(Set<String> set, Screens[] screens) {
		Stream.of(screens).forEach(screen -> {
			String screenId = screen.name();
			boolean isNew = set.add(screenId);
			if (!isNew) {
				throw new RuntimeException(
						String.format("Id - '%s' is repeated  for field %s , please resolve  it",
								screenId));
			}
		});
	}

	public static void persistScreenPermissionsIfNot(ScreenPermissionDAL dataAccess) {

		Set<String> allScreens = new HashSet<>();

		checkedInsertInSet(allScreens, UserScreens.class.getEnumConstants());
		checkedInsertInSet(allScreens, AdminScreens.class.getEnumConstants());

		Set<String> screenIdsInDb = dataAccess._findAll().stream()
				.map(ScreenPermission::getId)
				.collect(Collectors.toSet());

		List<ScreenPermission> screensToPersist = allScreens.stream()
				.filter(id -> !screenIdsInDb.contains(id))
				.map(id -> ScreenPermission.of(id))
				.collect(Collectors.toList());

		dataAccess._insert(screensToPersist);
	}

}
