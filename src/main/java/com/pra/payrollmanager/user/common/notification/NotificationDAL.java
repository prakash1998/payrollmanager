package com.pra.payrollmanager.user.common.notification;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDALWithCompany;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.security.authorization.ResourceFeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class NotificationDAL extends AuditDALWithCompany<ObjectId, Notification> {

	@Override
	public EntityName entity() {
		return EntityName.NOTIFICATION;
	}

	@Override
	public FeaturePermission apiPermission() {
		return ResourceFeaturePermissions.USER__NOTIFICATIONS;
	}

	@Override
	protected Notification injectAuditInfoOnCreate(Notification obj) {
		return obj;
	}

	@Override
	protected Notification injectAuditInfoOnUpdate(Notification dbObj, Notification obj) {
		return obj;
	}

}
