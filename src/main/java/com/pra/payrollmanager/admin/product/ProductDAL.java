package com.pra.payrollmanager.admin.product;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.ResourceAuditDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.user.root.permissions.resource.ResourcePermissionDAL;
import com.pra.payrollmanager.utils.DBQueryUtils;

@Repository
public class ProductDAL extends ResourceAuditDAL<ProductDAO> {

	public ProductDAL(ResourcePermissionDAL resourcePermissionDAL) {
		super(resourcePermissionDAL);
	}

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.PRODUCT;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.RESOURCE__PRODUCT;
	}

	public List<ProductDAO> findWithAccessControl() {
		Query query = Query.query(DBQueryUtils.idInArrayCriteria(authorityService().getUserResourceIds().toArray()));
		return super.findWithoutAuditInfo(query);
	}

}
