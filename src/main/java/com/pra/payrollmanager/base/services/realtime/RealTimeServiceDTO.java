package com.pra.payrollmanager.base.services.realtime;

import java.util.Set;

import com.pra.payrollmanager.base.BaseDAOWithDTO;
import com.pra.payrollmanager.base.BaseDTO;
import com.pra.payrollmanager.base.dal.BaseDALWithCompanyPrefix;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;
import com.pra.payrollmanager.message.MessageUtil;
import com.pra.payrollmanager.message.WSMessage;

public interface RealTimeServiceDTO<PK,
		DAO extends BaseDAOWithDTO<PK, DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends BaseDALWithCompanyPrefix<PK, DAO>>
		extends RealTimeServiceDAO<PK, DAO, DAL> {

	default DTO create(MessageSendingService messageService,
			DAL dataAccessLayer,
			String topic,
			Set<String> targetedUserIds,
			DTO obj) throws DuplicateDataEx {
		DTO savedObj = dataAccessLayer.create(obj.toDAO()).toDTO();
		messageService.send(topic,
				WSMessage.of(MessageUtil.insertDataMessage(savedObj), targetedUserIds));
		return savedObj;
	}

	default DTO update(MessageSendingService messageService,
			DAL dataAccessLayer,
			String topic,
			Set<String> targetedUserIds,
			DTO obj) throws DataNotFoundEx {
		DTO updatedObj = dataAccessLayer.update(obj.toDAO()).toDTO();
		messageService.send(topic,
				WSMessage.of(MessageUtil.updateDataMessage(updatedObj), targetedUserIds));
		return updatedObj;
	}

	default void delete(MessageSendingService messageService,
			DAL dataAccessLayer,
			String topic,
			Set<String> targetedUserIds,
			DTO obj) throws DataNotFoundEx {
		dataAccessLayer.delete(obj.toDAO());
		messageService.send(topic,
				WSMessage.of(MessageUtil.deleteDataMessage(obj), targetedUserIds));
	}

}
