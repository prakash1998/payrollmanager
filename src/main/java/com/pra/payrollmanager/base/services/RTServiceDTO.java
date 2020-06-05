package com.pra.payrollmanager.base.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.base.data.BaseDTO;
import com.pra.payrollmanager.base.data.WithDTO;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;
import com.pra.payrollmanager.security.authorization.AuthorityService;

abstract public class RTServiceDTO<PK,
		DAO extends BaseDAO<PK> & WithDTO<DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends BaseDAL<PK, DAO>>
		implements BaseServiceDTO<PK, DAO, DTO, DAL>, BaseRTService<DTO> {

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

	@Autowired
	protected AuthorityService authorityService;

	@Override
	public AuthorityService authorityService() {
		return authorityService;
	}

	@Override
	public DTO create(DTO obj) throws DuplicateDataEx, AnyThrowable {
		DTO savedObj = BaseServiceDTO.super.create(obj);
		sendCreateMessage(savedObj);
		return savedObj;
	}

	@Override
	public DTO update(DTO obj) throws DataNotFoundEx, AnyThrowable {
		DTO updatedObj = BaseServiceDTO.super.update(obj);
		sendUpdateMessage(updatedObj);
		return updatedObj;
	}

	// @Override
	// public DTO upsert(DTO obj) throws AnyThrowable {
	// DAO daoObj = obj.toDAO();
	// DTO upserted;
	// if (dataAccessLayer().exists(daoObj)) {
	// upserted = dataAccessLayer().save(daoObj).toDTO();
	// sendUpdateMessage(upserted);
	// } else {
	// upserted = dataAccessLayer().insert(daoObj).toDTO();
	// sendCreateMessage(upserted);
	// }
	// return upserted;
	// }

	@Override
	public DTO delete(DTO obj) throws DataNotFoundEx, AnyThrowable {
		DTO deletedObj = BaseServiceDTO.super.delete(obj);
		sendDeleteMessage(deletedObj);
		return deletedObj;
	}

	@Override
	public DTO deleteById(PK key) throws DataNotFoundEx, AnyThrowable {
		DTO deletedObj = BaseServiceDTO.super.deleteById(key);
		sendDeleteMessage(deletedObj);
		return deletedObj;
	}


}
