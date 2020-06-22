package com.pra.payrollmanager.apputils.filemanager.open;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.apputils.filemanager.AppFile;
import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class CommonAppFileDAL extends AuditDAL<ObjectId, AppFile> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.COMMON_FILES;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.BASE__FILES;
	}

}
