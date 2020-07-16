package com.pra.payrollmanager.user.root.permissions.security;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.RestrictedDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.security.authentication.user.SecurityUserPermissionService;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.security.authorization.SecurityPermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.utils.DBQueryUtils;

@Repository
public class SecurityPermissionDAL extends RestrictedDAL<String, SecurityPermission> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.PERMISSION;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.ROOT__PERMISSIONS;
	}

	@Autowired
	SecurityUserPermissionService userPermissionService;

	@Override
	public Predicate<SecurityPermission> hasAccessToItem() {
		return item -> userPermissionService.loadPermissionsForUser()
				.contains(item.getNumericId());
	}

	@Override
	public Criteria findAllAccessCriteria() {
		return DBQueryUtils.idInArrayCriteria(
				userPermissionService.loadPermissionsForUser()
						.stream()
						.map(item -> SecurityPermissions.universalSecurityPermissionMap.get(item).getId())
						.toArray());
	}
}
