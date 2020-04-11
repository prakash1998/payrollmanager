package com.pra.payrollmanager.base.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.BaseDAO;
import com.pra.payrollmanager.base.dal.BaseDALWithCompanyPrefix;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

import lombok.Setter;

abstract public class BaseServiceDAO<PK,
		DAO extends BaseDAO<PK>,
		DAL extends BaseDALWithCompanyPrefix<PK, DAO>> {

	@Autowired
	@Setter
	protected DAL dataAccessLayer;

	public boolean exists(DAO obj) {
		return dataAccessLayer.exists(obj);
	}

	public boolean existsById(PK key) {
		return dataAccessLayer.existsById(key);
	}

	public DAO getById(PK id) throws DataNotFoundEx {
		return dataAccessLayer.findById(id);
	}

	public List<DAO> getAll() {
		return dataAccessLayer.findAll().stream()
				.collect(Collectors.toList());
	}

	public DAO create(DAO obj) throws DuplicateDataEx {
		return dataAccessLayer.create(obj);
	}

	public DAO update(DAO obj) throws DataNotFoundEx {
		return dataAccessLayer.update(obj);
	}

	public void delete(DAO obj) throws DataNotFoundEx {
		dataAccessLayer.delete(obj);
	}

	public void deleteById(PK key) throws DataNotFoundEx {
		dataAccessLayer.deleteById(key);
	}

}
