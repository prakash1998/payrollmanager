package com.pra.payrollmanager.base.services;

import com.pra.payrollmanager.base.dal.DataStoreService;
import com.pra.payrollmanager.base.data.BaseDAO;

public interface NewBaseRTService<PK,
		DAO extends BaseDAO<PK>,
		DATA,
		DAL extends DataStoreService<PK, DAO>>
		extends NewBaseService<PK, DAO, DATA, DAL>, BaseRTService<PK> {
	
// not used
}
