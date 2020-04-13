package com.pra.payrollmanager.base.dal.next;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.BaseDAO;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.CheckedException;
import com.pra.payrollmanager.security.authorization.permission.api.ApiPermission;

public interface DataStoreService<PK, DAO extends BaseDAO<PK>> {

	MongoTemplate mongoTemplate();

	EntityName entity();

	Class<DAO> daoClazz();

	String tableName();
	
	ApiPermission apiPermission();

	default boolean exists(DAO obj) {
		return this.existsById(obj.primaryKeyValue());
	}

	default boolean existsById(PK key) {
		return mongoTemplate().exists(Query.query(Criteria.where("_id").is(key)),
				daoClazz(), this.tableName());
	}

	default List<DAO> findAll() {
		return mongoTemplate().findAll(daoClazz(), this.tableName());
	}

	default DAO findById(PK key) throws DataNotFoundEx {
		if (this.existsById(key)) {
			return mongoTemplate().findById(key, daoClazz(), this.tableName());
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(key));
		}
	}

	default List<DAO> findWith(Query query) {
		return mongoTemplate().find(query, daoClazz(), this.tableName());
	}

	default DAO findOneWith(Query query) {
		return mongoTemplate().findOne(query, daoClazz(), this.tableName());
	}

	default DAO create(DAO obj) throws DuplicateDataEx {
		if (this.exists(obj)) {
			throw CheckedException.duplicateEx(entity(), String.valueOf(obj.primaryKeyValue()));
		} else {
			return mongoTemplate().insert(obj, this.tableName());
		}
	}

	@Transactional
	default Collection<DAO> createAll(Collection<DAO> objList) throws DuplicateDataEx {
		try {
			return mongoTemplate().insert(objList, this.tableName());
		} catch (Exception e) {
			throw new DuplicateDataEx(String.format("Exception When bulk inserting %s", entity()), e);
		}
	}

	default DAO update(DAO obj) throws DataNotFoundEx {
		DAO dbObj = this.findById(obj.primaryKeyValue());
		if (dbObj != null) {
			if (obj.equals(dbObj)) {
				return dbObj;
			} else {
				return mongoTemplate().save(obj, this.tableName());
			}
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(obj.primaryKeyValue()));
		}
	}

	default DAO delete(DAO obj) throws DataNotFoundEx {
		DAO dbObj = this.findById(obj.primaryKeyValue());
		if (dbObj != null) {
			mongoTemplate().remove(obj, this.tableName());
			return dbObj;
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(obj.primaryKeyValue()));
		}
	}

	default DAO deleteById(PK key) throws DataNotFoundEx {
		DAO obj = this.findById(key);
		if (obj != null) {
			this.deleteWith(Query.query(Criteria.where("_id").is(key)));
			return obj;
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(key));
		}
	}

	default void deleteWith(Query query) {
		mongoTemplate().remove(query, daoClazz(), this.tableName());
	}

}
