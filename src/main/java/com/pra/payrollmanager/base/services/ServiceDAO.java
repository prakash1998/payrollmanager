package com.pra.payrollmanager.base.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.dal.DataStoreService;
import com.pra.payrollmanager.base.data.BaseDAO;

abstract public class ServiceDAO<PK,
		DAO extends BaseDAO<PK>,
		DAL extends DataStoreService<PK, DAO>>
		implements NewBaseServiceDAO<PK, DAO, DAL> {

	@Autowired
	protected DAL dataAccessLayer;

	@Override
	public DAL dataAccessLayer() {
		return dataAccessLayer;
	}
}
