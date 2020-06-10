package com.pra.payrollmanager.base.services;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;

abstract public class ServiceDAO<PK,
		DAO extends BaseDAO<PK>,
		DAL extends BaseDAL<PK, DAO>>
		extends ServiceBeans<DAL>
		implements BaseServiceDAO<PK, DAO, DAL> {

}
