package com.pra.payrollmanager.base.services.next;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.BaseDAO;
import com.pra.payrollmanager.base.dal.next.DataStoreService;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;

abstract public class RTServiceDAO<PK,
		DAO extends BaseDAO<PK>,
		DAL extends DataStoreService<PK, DAO>>
		implements NewBaseServiceDAO<PK, DAO, DAL>, BaseRTService<PK> {

	@Autowired
	protected DAL dataAccessLayer;

	@Override
	public DAL dataAccessLayer() {
		return dataAccessLayer;
	}

	@Autowired
	protected MessageSendingService messageService;

	@Override
	public MessageSendingService messageService() {
		return messageService;
	}

	@Override
	public DAO create(DAO obj) throws DuplicateDataEx {
		DAO savedObj = NewBaseServiceDAO.super.create(obj);
		sendCreateMessage(savedObj);
		return savedObj;
	}

	@Override
	public DAO update(DAO obj) throws DataNotFoundEx {
		DAO updatedObj = NewBaseServiceDAO.super.update(obj);
		sendUpdateMessage(updatedObj);
		return updatedObj;
	}

	@Override
	public DAO delete(DAO obj) throws DataNotFoundEx {
		DAO deletedObj = NewBaseServiceDAO.super.delete(obj);
		sendDeleteMessage(obj.primaryKeyValue());
		return deletedObj;
	}

	@Override
	public DAO deleteById(PK key) throws DataNotFoundEx {
		DAO deletedObj = NewBaseServiceDAO.super.deleteById(key);
		sendDeleteMessage(key);
		return deletedObj;
	}
}
