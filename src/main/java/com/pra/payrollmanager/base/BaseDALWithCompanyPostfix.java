package com.pra.payrollmanager.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.CheckedException;
import com.pra.payrollmanager.security.authorization.AuthorityService;

public abstract class BaseDALWithCompanyPostfix<PK, DAO extends BaseDAO<PK>> {
	// , REPO extends BaseRepo<DAO, PK>

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private AuthorityService authorityService;

	protected abstract EntityName entity();

	private Class<DAO> daoClazz;

	@SuppressWarnings("unchecked")
	public BaseDALWithCompanyPostfix() {
		Type sooper = getClass().getGenericSuperclass();
		daoClazz = (Class<DAO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[1];
	}

	public String tableName() {
		return this.entity().table() + authorityService.getTablePostfix();
	}

	public boolean exists(DAO obj) {
		return this.existsById(obj.primaryKeyValue());
	}

	public boolean existsById(PK key) {
		return mongoTemplate.exists(Query.query(Criteria.where("_id").is(key)), daoClazz, this.tableName());
	}

	public List<DAO> findAll() {
		return mongoTemplate.findAll(daoClazz, this.tableName());
	}

	public DAO findById(PK key) throws DataNotFoundEx {
		if (this.existsById(key)) {
			return mongoTemplate.findById(key, daoClazz, this.tableName());
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(key));
		}
	}

	public List<DAO> findWith(Query query) {
		return mongoTemplate.find(query, daoClazz, this.tableName());
	}

	public DAO findOneWith(Query query) {
		return mongoTemplate.findOne(query, daoClazz, this.tableName());
	}

	public void create(DAO obj) throws DuplicateDataEx {
		if (this.exists(obj)) {
			throw CheckedException.duplicateEx(entity(), String.valueOf(obj.primaryKeyValue()));
		} else {
			mongoTemplate.insert(obj, this.tableName());
		}
	}

	@Transactional
	public void createAll(Collection<DAO> objList) throws DuplicateDataEx {
		try {
			mongoTemplate.insert(objList, this.tableName());
		} catch (Exception e) {
			throw new DuplicateDataEx(String.format("Exception When bulk inserting %s", entity()), e);
		}
	}

	public void update(DAO obj) throws DataNotFoundEx {
		if (this.exists(obj)) {
			mongoTemplate.save(obj, this.tableName());
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(obj.primaryKeyValue()));
		}
	}

	public void delete(DAO obj) throws DataNotFoundEx {
		if (this.exists(obj)) {
			mongoTemplate.remove(obj, this.tableName());
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(obj.primaryKeyValue()));
		}
	}

	public void deleteById(PK key) throws DataNotFoundEx {
		if (this.existsById(key)) {
			this.deleteWith(Query.query(Criteria.where("_id").is(key)));
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(key));
		}
	}

	public void deleteWith(Query query) {
		mongoTemplate.remove(query, daoClazz, this.tableName());
	}

}
