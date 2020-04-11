package com.pra.payrollmanager.base.services.next;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.BaseDAOWithDTO;
import com.pra.payrollmanager.base.BaseDTO;
import com.pra.payrollmanager.base.dal.next.DataStoreService;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;

abstract public class RTServiceDTO<PK,
		DAO extends BaseDAOWithDTO<PK, DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends DataStoreService<PK, DAO>>
		implements NewBaseServiceDTO<PK, DAO, DTO, DAL>, BaseRTService<PK> {

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
	public DTO create(DTO obj) throws DuplicateDataEx {
		DTO savedObj = NewBaseServiceDTO.super.create(obj);
		sendCreateMessage(savedObj);
		return savedObj;
	}

	@Override
	public DTO update(DTO obj) throws DataNotFoundEx {
		DTO updatedObj = NewBaseServiceDTO.super.update(obj);
		sendUpdateMessage(updatedObj);
		return updatedObj;
	}

	@Override
	public DTO delete(DTO obj) throws DataNotFoundEx {
		DTO deletedObj = NewBaseServiceDTO.super.delete(obj);
		sendDeleteMessage(deletedObj.toDAO().primaryKeyValue());
		return deletedObj;
	}

	@Override
	public DTO deleteById(PK key) throws DataNotFoundEx {
		DTO deletedObj = NewBaseServiceDTO.super.deleteById(key);
		sendDeleteMessage(key);
		return deletedObj;
	}

}
