package com.pra.payrollmanager.base.services.next;

import java.util.List;

import com.pra.payrollmanager.base.BaseDAO;
import com.pra.payrollmanager.base.dal.next.DataStoreService;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

public interface NewBaseService<PK,
		DAO extends BaseDAO<PK>,
		DATA,
		DAL extends DataStoreService<PK, DAO>> {

	DAL dataAccessLayer();

	boolean exists(DATA obj);

	List<DATA> getAll();

	DATA create(DATA obj) throws DuplicateDataEx;

	DATA update(DATA obj) throws DataNotFoundEx;

	DATA delete(DATA obj) throws DataNotFoundEx;

	DATA getById(PK id) throws DataNotFoundEx;

	DATA deleteById(PK key) throws DataNotFoundEx;
	
	default boolean existsById(PK key) {
		return dataAccessLayer().existsById(key);
	}

}
