package com.pra.payrollmanager.base.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.dal.DataStoreService;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.base.data.BaseDTO;
import com.pra.payrollmanager.base.data.WithDTO;

import lombok.Setter;

abstract public class ServiceDTO<PK,
		DAO extends BaseDAO<PK> & WithDTO<DTO> ,
		DTO extends BaseDTO<DAO>,
		DAL extends DataStoreService<PK, DAO>>
		implements NewBaseServiceDTO<PK, DAO, DTO, DAL> {

	@Autowired
	@Setter
	protected DAL dataAccessLayer;

	@Override
	public DAL dataAccessLayer() {
		return dataAccessLayer;
	}

}
