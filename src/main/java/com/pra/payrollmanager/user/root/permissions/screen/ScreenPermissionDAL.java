package com.pra.payrollmanager.user.root.permissions.screen;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.base.dal.RestrictedDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.security.authentication.user.SecurityUserPermissionService;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.endpoint.EndpointPermission;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.utils.DBQueryUtils;

@Repository
public class ScreenPermissionDAL extends RestrictedDAL<String, ScreenPermission> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.SCREEN_PERMISSION;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.ROOT__PERMISSIONS;
	}

	@Autowired
	SecurityUserPermissionService userPermissionService;

	@Override
	public Predicate<ScreenPermission> hasAccessToItem() {
		return item -> userPermissionService.loadScreensForUser()
				.contains(item.getId());
	}

	@Override
	public Criteria findAllAccessCriteria() {
		return DBQueryUtils.idInArrayCriteria(
				userPermissionService.loadScreensForUser().toArray());
	}

}
