package com.pra.payrollmanager.base.services.next;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.BaseDAOWithDTO;
import com.pra.payrollmanager.base.BaseDTO;
import com.pra.payrollmanager.base.dal.next.DataStoreService;

abstract public class ServiceDTO<PK,
		DAO extends BaseDAOWithDTO<PK, DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends DataStoreService<PK, DAO>>
		implements NewBaseServiceDTO<PK, DAO, DTO, DAL> {

	@Autowired
	protected DAL dataAccessLayer;

	@Override
	public DAL dataAccessLayer() {
		return dataAccessLayer;
	}

}
