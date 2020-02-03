package com.pra.payrollmanager.service.base;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.dal.base.BaseDAL;
import com.pra.payrollmanager.dao.base.BaseDAO;
import com.pra.payrollmanager.dao.base.WithDTO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

import lombok.Setter;

abstract public class BaseServiceDAO<PK,
		DAO extends BaseDAO<PK>,
		DAL extends BaseDAL<PK, DAO, ?>> {

	@Autowired
	@Setter
	protected DAL dataAccessLayer;

	public void create(DAO obj) throws DuplicateDataEx {
		dataAccessLayer.create(obj);
	}

	public void update(DAO obj) throws DataNotFoundEx {
		dataAccessLayer.update(obj);
	}

	public void delete(DAO obj) throws DataNotFoundEx {
		dataAccessLayer.delete(obj);
	}

	public List<DAO> getAll() {
		return dataAccessLayer.getAll().stream()
				.collect(Collectors.toList());
	}

	public DAO getById(PK id) throws DataNotFoundEx {
		return dataAccessLayer.findById(id);
	}
}
