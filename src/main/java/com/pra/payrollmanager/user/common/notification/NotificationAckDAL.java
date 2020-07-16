package com.pra.payrollmanager.user.common.notification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.base.data.BulkOp;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class NotificationAckDAL extends AuditDAL<ObjectId, NotificationAck> {

	@Autowired
	SecurityUserService userService;

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.NOTIFICATION_ACK;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.USER__NOTIFICATIONS;
	}

	@Override
	public NotificationAck setAuditInfoOnCreate(NotificationAck obj) {
		return obj;
	}

	@Override
	public NotificationAck setAuditInfoOnUpdate(NotificationAck dbObj, NotificationAck obj) {
		return obj;
	}

	@Override
	public NotificationAck setAuditInfoOnDelete(NotificationAck obj) {
		obj.setDeletedBy(null);
		obj.setDeletedDate(null);
		return obj;
	}

	void addUsersForAck(ObjectId notificationId, Set<String> users) throws DuplicateDataEx {
		Set<String> usersForAck = users;
		if (users.isEmpty()) {
			String companyId = authorityService.getSecurityCompany().getId();
			usersForAck = userService.getAll().stream()
					.map(usr -> companyId + "-" + usr.getUsername())
					.collect(Collectors.toSet());
		}

		super.bulkOp(BulkOp.fromAdded(
				usersForAck.stream()
						.map(usr -> NotificationAck.builder()
								.notificationId(notificationId)
								.reciever(usr)
								.build())
						.collect(Collectors.toList())));
	}

	List<NotificationAck> findByReciever(String user) {
		return super.findWith(Query.query(Criteria.where("reciever").is(user)));
	}

	List<NotificationAck> findByNotificationId(ObjectId notificationId) {
		return super.findWith(Query.query(Criteria.where("notificationId").is(notificationId)));
	}

	void ackForUser(ObjectId notificationId, String user) {
		super.deleteWith(Query.query(Criteria.where("notificationId").is(notificationId)
				.and("reciever").is(user)));
	}
}
