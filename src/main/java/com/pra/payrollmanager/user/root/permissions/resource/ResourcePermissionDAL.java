package com.pra.payrollmanager.user.root.permissions.resource;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.RestrictedDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.security.authentication.user.SecurityUserPermissionService;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.utils.DBQueryUtils;

@Repository
public class ResourcePermissionDAL extends RestrictedDAL<ObjectId, ResourceDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.RESOURCES;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.ROOT__PERMISSIONS;
	}

	@Autowired
	SecurityUserPermissionService userPermissionService;

	@Override
	public Criteria findAllAccessCriteria() {
		return DBQueryUtils.idInArrayCriteria(
				userPermissionService.loadResourcesForUser().toArray());
	}

}
