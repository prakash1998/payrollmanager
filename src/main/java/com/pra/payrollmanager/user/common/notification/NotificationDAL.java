package com.pra.payrollmanager.user.common.notification;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class NotificationDAL extends AuditDAL<ObjectId, Notification> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.NOTIFICATION;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.USER__NOTIFICATIONS;
	}

	@Override
	public Notification setAuditInfoOnCreate(Notification obj) {
		return obj;
	}

	@Override
	public Notification setAuditInfoOnUpdate(Notification dbObj, Notification obj) {
		return obj;
	}

}
