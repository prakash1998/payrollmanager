package com.pra.payrollmanager.base.services.realtime;

import java.util.Set;

import com.pra.payrollmanager.base.BaseDAO;
import com.pra.payrollmanager.base.dal.BaseDALWithCompanyPrefix;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;
import com.pra.payrollmanager.message.MessageUtil;
import com.pra.payrollmanager.message.WSMessage;

public interface RealTimeServiceDAO<PK, DAO extends BaseDAO<PK>, DAL extends BaseDALWithCompanyPrefix<PK, DAO>> {

	default DAO create(MessageSendingService messageService,
			DAL dataAccessLayer,
			String topic,
			Set<String> targetedUserIds,
			DAO obj) throws DuplicateDataEx {
		DAO savedObj = dataAccessLayer.create(obj);
		messageService.send(topic,
				WSMessage.of(MessageUtil.insertDataMessage(savedObj), targetedUserIds));
		return savedObj;
	}

	default DAO update(MessageSendingService messageService,
			DAL dataAccessLayer,
			String topic,
			Set<String> targetedUserIds,
			DAO obj) throws DataNotFoundEx {
		DAO updatedObj = dataAccessLayer.update(obj);
		messageService.send(topic,
				WSMessage.of(MessageUtil.updateDataMessage(updatedObj), targetedUserIds));
		return updatedObj;
	}

	default void delete(MessageSendingService messageService,
			DAL dataAccessLayer,
			String topic,
			Set<String> targetedUserIds,
			DAO obj) throws DataNotFoundEx {
		dataAccessLayer.delete(obj);
		messageService.send(topic,
				WSMessage.of(MessageUtil.deleteDataMessage(obj.primaryKeyValue()), targetedUserIds));
	}

	default void deleteById(MessageSendingService messageService,
			DAL dataAccessLayer,
			String topic,
			Set<String> targetedUserIds,
			PK key) throws DataNotFoundEx {
		dataAccessLayer.deleteById(key);
		messageService.send(topic,
				WSMessage.of(MessageUtil.deleteDataMessage(key), targetedUserIds));
	}

}
