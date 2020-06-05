package com.pra.payrollmanager.base.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.base.data.BaseDTO;
import com.pra.payrollmanager.base.data.WithDTO;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

public interface BaseServiceDTO<PK,
		DAO extends BaseDAO<PK> & WithDTO<DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends BaseDAL<PK, DAO>>
		extends BaseService<PK, DAO, DTO, DAL> {

	@Override
	default boolean exists(DTO obj) {
		return dataAccessLayer().exists(obj.toDAO());
	}

	@Override
	default DTO getById(PK id) throws DataNotFoundEx, AnyThrowable {
		return dataAccessLayer().findById(id).toDTO();
	}

	@Override
	default List<DTO> getByIds(Set<PK> ids) {
		return dataAccessLayer().findByIds(ids).stream()
				.map(obj -> obj.toDTO())
				.collect(Collectors.toList());
	}

	@Override
	default List<DTO> getAll() {
		return dataAccessLayer().findAll().stream()
				.map(item -> item.toDTO())
				.collect(Collectors.toList());
	}

	@Override
	default DTO create(DTO obj) throws DuplicateDataEx, AnyThrowable {
		return dataAccessLayer().create(obj.toDAO()).toDTO();
	}

	@Override
	default DTO update(DTO obj) throws DataNotFoundEx, AnyThrowable {
		return dataAccessLayer().update(obj.toDAO()).toDTO();
	}

	// @Override
	// default DTO upsert(DTO obj) throws AnyThrowable {
	// return dataAccessLayer().upsert(obj.toDAO()).toDTO();
	// }

	@Override
	default DTO delete(DTO obj) throws DataNotFoundEx, AnyThrowable {
		return dataAccessLayer().delete(obj.toDAO()).toDTO();
	}

	@Override
	default DTO deleteById(PK key) throws DataNotFoundEx, AnyThrowable {
		return dataAccessLayer().deleteById(key).toDTO();
	}

}
