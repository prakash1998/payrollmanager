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
public class Notification extends BaseAuditDAO<ObjectId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5396942872374916574L;

	@Id
	private ObjectId id;

	private String sender;
	private String display;

	private NotificationType type;

	private String referenceId;

	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}

}
