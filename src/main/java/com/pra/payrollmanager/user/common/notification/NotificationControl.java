package com.pra.payrollmanager.user.common.notification;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.base.BaseControl;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.response.dto.Response;

@RestController
@RequestMapping("notifications")
public class NotificationControl extends BaseControl<NotificationService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<Notification>> getAllNotifications() throws DataNotFoundEx {
		return Response.payload(service.getActiveNotifications());
	}

	@DeleteMapping(value = "ack", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Notification> acknowledgeNotification(@Valid @RequestBody Notification notification)
			throws AnyThrowable, DataNotFoundEx {
		return Response.payload(service.acknowledge(notification));
	}

}
