package com.pra.payrollmanager.base.services.next;

import java.util.List;

import com.pra.payrollmanager.base.BaseDAO;
import com.pra.payrollmanager.base.dal.next.DataStoreService;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

public interface NewBaseServiceDAO<PK,
		DAO extends BaseDAO<PK>,
		DAL extends DataStoreService<PK, DAO>>
		extends NewBaseService<PK, DAO, DAO, DAL> {

	@Override
	default boolean exists(DAO obj) {
		return dataAccessLayer().exists(obj);
	}

	@Override
	default DAO getById(PK id) throws DataNotFoundEx {
		return dataAccessLayer().findById(id);
	}

	@Override
	default List<DAO> getAll() {
		return dataAccessLayer().findAll();
	}

	@Override
	default DAO create(DAO obj) throws DuplicateDataEx {
		return dataAccessLayer().create(obj);
	}

	@Override
	default DAO update(DAO obj) throws DataNotFoundEx {
		return dataAccessLayer().update(obj);
	}

	@Override
	default DAO delete(DAO obj) throws DataNotFoundEx {
		return dataAccessLayer().delete(obj);
	}

	default DAO deleteById(PK key) throws DataNotFoundEx {
		return dataAccessLayer().deleteById(key);
	}
}
