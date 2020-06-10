package com.pra.payrollmanager.base.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;

abstract public class RTServiceDAO<PK,
		DAO extends BaseDAO<PK>,
		DAL extends BaseDAL<PK, DAO>>
		extends ServiceBeans<DAL>
		implements BaseServiceDAO<PK, DAO, DAL>, BaseRTService<DAO> {

	@Autowired
	protected MessageSendingService messageService;

	@Override
	public MessageSendingService messageService() {
		return messageService;
	}

	@Override
	public DAO create(DAO obj) throws DuplicateDataEx {
		DAO savedObj = BaseServiceDAO.super.create(obj);
		sendCreateMessage(savedObj);
		return savedObj;
	}

	public DAO create(DAO obj, Set<String> targetedUsers) throws DuplicateDataEx {
		DAO savedObj = BaseServiceDAO.super.create(obj);
		sendCreateMessage(savedObj, targetedUsers);
		return savedObj;
	}

	@Override
	public DAO update(DAO obj) throws DataNotFoundEx {
		DAO updatedObj = BaseServiceDAO.super.update(obj);
		sendUpdateMessage(updatedObj);
		return updatedObj;
	}

	// @Override
	// public DAO upsert(DAO obj) throws AnyThrowable {
	// DAO upserted;
	// if (dataAccessLayer().exists(obj)) {
	// upserted = dataAccessLayer().save(obj);
	// sendUpdateMessage(upserted);
	// } else {
	// upserted = dataAccessLayer().insert(obj);
	// sendCreateMessage(upserted);
	// }
	// return upserted;
	// }

	@Override
	public DAO delete(DAO obj) throws DataNotFoundEx {
		DAO deletedObj = BaseServiceDAO.super.delete(obj);
		sendDeleteMessage(deletedObj);
		return deletedObj;
	}

	@Override
	public DAO deleteById(PK key) throws DataNotFoundEx {
		DAO deletedObj = BaseServiceDAO.super.deleteById(key);
		sendDeleteMessage(deletedObj);
		return deletedObj;
	}

}
