package com.pra.payrollmanager.base.services;

import java.util.List;
import java.util.Set;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

public interface BaseService<PK,
		DAO extends BaseDAO<PK>,
		DATA,
		DAL extends BaseDAL<PK, DAO>> {

	DAL dataAccessLayer();

	boolean exists(DATA obj);

	List<DATA> getAll();

	DATA create(DATA obj) throws DuplicateDataEx, AnyThrowable;

	DATA update(DATA obj) throws DataNotFoundEx, AnyThrowable;

//	DATA upsert(DATA obj) throws AnyThrowable;

	DATA delete(DATA obj) throws DataNotFoundEx, AnyThrowable;

	DATA getById(PK id) throws DataNotFoundEx, AnyThrowable;

	List<DATA> getByIds(Set<PK> id) throws DataNotFoundEx, AnyThrowable;

	DATA deleteById(PK key) throws DataNotFoundEx, AnyThrowable;

	default boolean existsById(PK key) {
		return dataAccessLayer().existsById(key);
	}

}