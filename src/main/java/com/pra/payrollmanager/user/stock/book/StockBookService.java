package com.pra.payrollmanager.user.stock.book;

import java.util.Optional;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.admin.product.ProductDAL;
import com.pra.payrollmanager.admin.product.ProductDAO;
import com.pra.payrollmanager.apputils.mailer.MailerService;
import com.pra.payrollmanager.base.services.AuditRTServiceDTO;
import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.security.authentication.user.SecurityUserPermissionService;
import com.pra.payrollmanager.user.common.notification.NotificationService;
import com.pra.payrollmanager.user.common.notification.NotificationType;

@Service
public class StockBookService
		extends AuditRTServiceDTO<ObjectId, StockBookDAO, StockBookDTO, StockBookDAL> {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ProductDAL productDAL;

	@Autowired
	private SecurityUserPermissionService userPermissionService;

	@Override
	public String mqTopic() {
		return KafkaTopics.STOCK_BOOK;
	}

	private void sendNotification(StockBookDTO obj) throws DataNotFoundEx, AnyThrowable {

		ObjectId productId = obj.getProductId();

		ProductDAO product = productDAL.findById(productId);

		notificationService.sendNotification(NotificationType.STOCKS,
				productId.toString(),
				String.format("%s is updated to %s",
						Optional.ofNullable(product.getName()).orElse(product.getId().toString()), obj.getQuantity()),
				targetedUserIds(obj));
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

	@Override
	public Set<String> targetedUserIds(StockBookDTO obj) {
		return userPermissionService.loadUsersWithResource(obj.getProductId());
	}

}
