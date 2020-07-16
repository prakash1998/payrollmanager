package com.pra.payrollmanager.user.root.permissions.endpoint;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.RestrictedDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.security.authentication.user.SecurityUserPermissionService;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.utils.DBQueryUtils;

@Repository
public class EndpointPermissionDAL extends RestrictedDAL<String, EndpointPermission> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.ENDPOINT_PERMISSION;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.ROOT__PERMISSIONS;
	}

	@Autowired
	SecurityUserPermissionService userPermissionService;

	@Override
	public Predicate<EndpointPermission> hasAccessToItem() {
		return item -> userPermissionService.loadEndpointsForUser()
				.contains(item.getId());
	}

	@Override
	public Function<List<EndpointPermission>, List<EndpointPermission>> filterItemsWithAccess() {
		Set<String> allowedEndpoints = userPermissionService.loadEndpointsForUser();
		return items -> items.stream().filter(item -> allowedEndpoints.contains(item.getId()))
				.collect(Collectors.toList());
	}

	@Override
	public Criteria findAllAccessCriteria() {
		return DBQueryUtils.idInArrayCriteria(
				userPermissionService.loadEndpointsForUser().toArray());
	}

}
