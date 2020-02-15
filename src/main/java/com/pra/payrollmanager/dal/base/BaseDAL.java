package com.pra.payrollmanager.dal.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.dao.base.BaseDAO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.EntityType;
import com.pra.payrollmanager.repository.base.BaseRepo;

public abstract class BaseDAL<PK, DAO extends BaseDAO<PK>, REPO extends BaseRepo<DAO, PK>> {

	@Autowired
	protected MongoTemplate mongoTemplate;

//	@Autowired
//	protected REPO repo;

	protected abstract EntityType entityType();
	
	private Class<DAO> daoClazz;

	public BaseDAL() {
		Type sooper = getClass().getGenericSuperclass();
		daoClazz = (Class<DAO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[1];
	}
	
	protected abstract String tableName();

	public void create(DAO obj) throws DuplicateDataEx {
		mongoTemplate.insert(obj,tableName());
//		Optional<DAO> objFromDb = repo.findById(obj.primaryKeyValue());
//		if (objFromDb.isPresent())
//			throw CheckedException.duplicateEx(entityType(), String.valueOf(obj.primaryKeyValue()));
//		else
//			repo.save(obj);
	}

	public void update(DAO obj) throws DataNotFoundEx {
		mongoTemplate.save(obj,tableName());
//		Optional<DAO> objFromDb = repo.findById(obj.primaryKeyValue());
//		if (objFromDb.isPresent())
//			repo.save(obj);
//		else
//			throw CheckedException.notFoundEx(entityType(), String.valueOf(obj.primaryKeyValue()));
	}

	public void delete(DAO obj) throws DataNotFoundEx {
		mongoTemplate.remove(obj, tableName());
//		deleteById(obj.primaryKeyValue());
	}

	public void deleteById(PK key) throws DataNotFoundEx {
		delete(findById(key));
		
//		Optional<DAO> obj = repo.findById(key);
//		if (obj.isPresent())
//			repo.deleteById(obj.get().primaryKeyValue());
//		else
//			throw CheckedException.notFoundEx(entityType(), String.valueOf(key));
	}

	public List<DAO> getAll() {
		return mongoTemplate.findAll(daoClazz, tableName());
//		return repo.findAll();
	}

	public DAO findById(PK key) throws DataNotFoundEx {
		return mongoTemplate.findById(key, daoClazz,tableName());
//		Optional<DAO> obj = repo.findById(key);
//		if (obj.isPresent())
//			return obj.get();
//		throw CheckedException.notFoundEx(entityType(), String.valueOf(key));
	}
}
