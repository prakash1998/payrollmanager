package com.pra.payrollmanager.base.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;

abstract public class ServiceDAO<PK,
		DAO extends BaseDAO<PK>,
		DAL extends BaseDAL<PK, DAO>>
		implements BaseServiceDAO<PK, DAO, DAL> {

	@Autowired
	protected DAL dataAccessLayer;

	@Override
	public DAL dataAccessLayer() {
		return dataAccessLayer;
	}
}
