package com.pra.payrollmanager.dal.base;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.dao.base.BaseDAO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.CheckedException;
import com.pra.payrollmanager.exception.util.EntityType;
import com.pra.payrollmanager.repository.base.BaseRepo;

public abstract class BaseDAL<PK, DAO extends BaseDAO<PK>, REPO extends BaseRepo<DAO, PK>> {

	@Autowired
	protected MongoTemplate mongoTemplate;

	@Autowired
	protected REPO repo;

	protected abstract EntityType entityType();

	public void create(DAO obj) throws DuplicateDataEx {
		Optional<DAO> objFromDb = repo.findById(obj.primaryKeyValue());
		if (objFromDb.isPresent())
			throw CheckedException.duplicateEx(entityType(), String.valueOf(obj.primaryKeyValue()));
		else
			repo.save(obj);
	}

	public void update(DAO obj) throws DataNotFoundEx {
		Optional<DAO> objFromDb = repo.findById(obj.primaryKeyValue());
		if (objFromDb.isPresent())
			repo.save(obj);
		else
			throw CheckedException.notFoundEx(entityType(), String.valueOf(obj.primaryKeyValue()));
	}

	public void delete(DAO obj) throws DataNotFoundEx {
		deleteById(obj.primaryKeyValue());
	}

	public void deleteById(PK key) throws DataNotFoundEx {
		Optional<DAO> obj = repo.findById(key);
		if (obj.isPresent())
			repo.deleteById(obj.get().primaryKeyValue());
		else
			throw CheckedException.notFoundEx(entityType(), String.valueOf(key));
	}

	public List<DAO> getAll() {
		return repo.findAll();
	}

	public DAO findById(PK key) throws DataNotFoundEx {
		Optional<DAO> obj = repo.findById(key);
		if (obj.isPresent())
			return obj.get();
		throw CheckedException.notFoundEx(entityType(), String.valueOf(key));
	}
}
