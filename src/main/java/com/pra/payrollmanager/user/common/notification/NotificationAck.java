package com.pra.payrollmanager.user.common.notification;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NotificationAck extends BaseAuditDAO<ObjectId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5396942872374916574L;

	@Id
	ObjectId id;

	private ObjectId notificationId;

	private String reciever;

	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}

}
