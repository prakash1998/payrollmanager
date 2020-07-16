package com.pra.payrollmanager.user.common.notification;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.services.AuditRTServiceDAO;
import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService extends AuditRTServiceDAO<ObjectId, Notification, NotificationDAL> {

	@Autowired
	NotificationAckDAL acknowledgeDal;

	@Override
	public String mqTopic() {
		return KafkaTopics.NOTIFICATIONS;
	}

	List<Notification> getActiveNotifications() throws DataNotFoundEx {
		List<NotificationAck> ackNotifications = acknowledgeDal.findByReciever(authorityService().getUserId());
		if (ackNotifications.isEmpty()) {
			return Collections.emptyList();
		}
		Set<ObjectId> notificationIds = ackNotifications.stream()
				.map(ack -> ack.getNotificationId())
				.collect(Collectors.toSet());
		return super.getByIds(notificationIds);
	}

	@Transactional
	public void sendNotification(NotificationType type, String reference, String display, Set<String> targetedUsers) {
		Notification savedNotification = super.create(
				Notification.builder()
						.type(type)
						.reference(reference)
						.display(display)
						.sender(authorityService().getUserName())
						.build(),
				targetedUsers);
		acknowledgeDal.addUsersForAck(savedNotification.getId(), targetedUsers);
	}

	@Transactional
	public Notification acknowledge(Notification notification) throws DataNotFoundEx {
		// Notification dbNotification = super.getById(notification.getId());

		String ackedBy = authorityService().getUserId();
		List<NotificationAck> recieverNotificaiton = acknowledgeDal.findByNotificationId(notification.getId());
		if (recieverNotificaiton.size() == 1 && recieverNotificaiton.get(0).getReciever().equals(ackedBy)) {
			super.delete(notification);
		}
		acknowledgeDal.ackForUser(notification.getId(), ackedBy);
		return notification;
	}

}
