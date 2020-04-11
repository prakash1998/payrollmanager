package com.pra.payrollmanager.base.services.realtime;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.BaseDAOWithDTO;
import com.pra.payrollmanager.base.BaseDTO;
import com.pra.payrollmanager.base.dal.BaseDALWithCompanyPrefix;
import com.pra.payrollmanager.base.services.BaseServiceDTO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;

abstract public class BaseRealTimeServiceDTO<PK,
		DAO extends BaseDAOWithDTO<PK, DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends BaseDALWithCompanyPrefix<PK, DAO>>
		extends BaseServiceDTO<PK, DAO, DTO, DAL> implements RealTimeServiceDTO<PK, DAO, DTO, DAL> {

	@Autowired
	protected MessageSendingService messageService;

	protected abstract String queueTopic();

	protected abstract Set<String> targetedUserIds();

	@Override
	public DAO create(DAO obj) throws DuplicateDataEx {
		return RealTimeServiceDTO.super.create(messageService, dataAccessLayer, queueTopic(), targetedUserIds(), obj);
	}

	@Override
	public DAO update(DAO obj) throws DataNotFoundEx {
		return RealTimeServiceDTO.super.update(messageService, dataAccessLayer, queueTopic(), targetedUserIds(), obj);
	}

	@Override
	public void delete(DAO obj) throws DataNotFoundEx {
		RealTimeServiceDTO.super.delete(messageService, dataAccessLayer, queueTopic(), targetedUserIds(), obj);
	}

	@Override
	public void deleteById(PK key) throws DataNotFoundEx {
		RealTimeServiceDTO.super.deleteById(messageService, dataAccessLayer, queueTopic(), targetedUserIds(), key);
	}

	@Override
	public DTO create(DTO obj) throws DuplicateDataEx {
		return RealTimeServiceDTO.super.create(messageService, dataAccessLayer, queueTopic(), targetedUserIds(),
				obj);
	}

	@Override
	public DTO update(DTO obj) throws DataNotFoundEx {
		return RealTimeServiceDTO.super.update(messageService, dataAccessLayer, queueTopic(), targetedUserIds(),
				obj);
	}

	@Override
	public void delete(DTO obj) throws DataNotFoundEx {
		RealTimeServiceDTO.super.delete(messageService, dataAccessLayer, queueTopic(), targetedUserIds(), obj);
	}

}
