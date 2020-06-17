package com.pra.payrollmanager.user.stock.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.AuditRTServiceDTO;
import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
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
	public StockBookDAO toDAO(StockBookDTO dto) {
		return super.toDAO(dto)
				.setId(dto.getProductId())
				.setNote(dto.getUpdateNote());
	}
	
	@Override
	public StockBookDTO toDTO(StockBookDAO dao) {
		return super.toDTO(dao)
				.setProductId(dao.getId())
				.setUpdateNote(dao.getNote());
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
