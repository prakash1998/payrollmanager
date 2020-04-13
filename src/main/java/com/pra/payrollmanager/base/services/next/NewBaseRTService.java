package com.pra.payrollmanager.base.services.next;

import com.pra.payrollmanager.base.BaseDAO;
import com.pra.payrollmanager.base.dal.next.DataStoreService;

public interface NewBaseRTService<PK,
		DAO extends BaseDAO<PK>,
		DATA,
		DAL extends DataStoreService<PK, DAO>>
		extends NewBaseService<PK, DAO, DATA, DAL>, BaseRTService<PK> {
	
// not used
}
