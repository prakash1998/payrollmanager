package com.pra.payrollmanager.base.services;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.base.data.BaseDTO;
import com.pra.payrollmanager.base.data.WithDTO;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;

abstract public class RTServiceDTO<PK,
		DAO extends BaseDAO<PK> & WithDTO<DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends BaseDAL<PK, DAO>>
		extends ServiceBeans<DAL>
		implements BaseServiceDTO<PK, DAO, DTO, DAL>, BaseRTService<DTO> {

	@SuppressWarnings("unchecked")
	@Override
	public Class<DTO> dtoClazz() {
		// specified in each class in hierarchy because we can access type parameter
		// class in immediate parent only
		Type sooper = getClass().getGenericSuperclass();
		return (Class<DTO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[2];
	}

	@Autowired
	protected MessageSendingService messageService;

	@Override
	public MessageSendingService messageService() {
		return messageService;
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
