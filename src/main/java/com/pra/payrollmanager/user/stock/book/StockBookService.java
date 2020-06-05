package com.pra.payrollmanager.user.stock.book;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.AuditRTServiceDTO;
import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.user.common.notification.NotificationService;
import com.pra.payrollmanager.user.common.notification.NotificationType;

@Service
public class StockBookService
		extends AuditRTServiceDTO<String, StockBookDAO, StockBookDTO, StockBookDAL> {

	@Autowired
	NotificationService notificationService;

	@Override
	public String mqTopic() {
		return KafkaTopics.STOCK_BOOK;
	}

	private void sendNotification(StockBookDTO obj) {
		notificationService.sendNotification(NotificationType.STOCKS, obj.getProductId(), targetedUserIds(obj));
	}

	@Override
	public StockBookDTO create(StockBookDTO obj) throws DuplicateDataEx, AnyThrowable {
		StockBookDTO createdObj = super.create(obj);
		sendNotification(createdObj);
		return createdObj;
	}

	@Override
	public StockBookDTO update(StockBookDTO obj) throws DataNotFoundEx, AnyThrowable {
		StockBookDTO updatedObj = super.update(obj);
		sendNotification(updatedObj);
		return updatedObj;
	}

}
