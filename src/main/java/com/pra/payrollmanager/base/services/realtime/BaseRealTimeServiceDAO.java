package com.pra.payrollmanager.base.services.realtime;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.BaseDAO;
import com.pra.payrollmanager.base.dal.BaseDALWithCompanyPrefix;
import com.pra.payrollmanager.base.services.BaseServiceDAO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;

abstract public class BaseRealTimeServiceDAO<PK,
		DAO extends BaseDAO<PK>,
		DAL extends BaseDALWithCompanyPrefix<PK, DAO>>
		extends BaseServiceDAO<PK, DAO, DAL> implements RealTimeServiceDAO<PK, DAO, DAL> {

	@Autowired
	protected MessageSendingService messageService;

	protected abstract String queueTopic();

	protected abstract Set<String> targetedUserIds();

	@Override
	public DAO create(DAO obj) throws DuplicateDataEx {
		return RealTimeServiceDAO.super.create(messageService, dataAccessLayer, queueTopic(), targetedUserIds(), obj);
	}

	@Override
	public DAO update(DAO obj) throws DataNotFoundEx {
		return RealTimeServiceDAO.super.update(messageService, dataAccessLayer, queueTopic(), targetedUserIds(), obj);
	}

	@Override
	public void delete(DAO obj) throws DataNotFoundEx {
		RealTimeServiceDAO.super.delete(messageService, dataAccessLayer, queueTopic(), targetedUserIds(), obj);
	}

	@Override
	public void deleteById(PK key) throws DataNotFoundEx {
		RealTimeServiceDAO.super.deleteById(messageService, dataAccessLayer, queueTopic(), targetedUserIds(), key);
	}

}
