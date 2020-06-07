package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.entity.EntityName;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.CheckedException;

public interface BaseDAL<PK, DAO extends BaseDAO<PK>> {

	MongoTemplate mongoTemplate();

	EntityName entity();

	Class<DAO> daoClazz();

	String tableName();

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

	default List<DAO> findByIds(Set<PK> keyList) {
		return findWith(Query.query(Criteria.where("_id").in(keyList)));
	}

	default DAO findById(PK key) throws DataNotFoundEx {
		DAO dbObj = this.findByIdUnchecked(key);
		if (dbObj != null) {
			return dbObj;
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(key));
		}
	}

	default DAO findByIdUnchecked(PK key) {
		return mongoTemplate().findById(key, daoClazz(), this.tableName());
	}

	default List<DAO> findWith(Query query) {
		return mongoTemplate().find(query, daoClazz(), this.tableName());
	}

	default DAO findOneWith(Query query) {
		return mongoTemplate().findOne(query, daoClazz(), this.tableName());
	}

	default DAO insert(DAO obj) {
		return mongoTemplate().insert(obj, this.tableName());
		// return obj;
	}

	default DAO save(DAO obj) {
		return mongoTemplate().save(obj, this.tableName());
		// return obj;
	}

	default List<DAO> deleteWith(Query query) {
		return mongoTemplate().findAllAndRemove(query, this.tableName());
	}

	default DAO create(DAO obj) throws DuplicateDataEx {
		if (this.exists(obj)) {
			throw CheckedException.duplicateEx(entity(), String.valueOf(obj.primaryKeyValue()));
		} else {
			return this.insert(obj);
			// return obj;
		}
	}
	
	default Collection<DAO> createMultiple(Collection<DAO> objList) throws DuplicateDataEx {
		if (objList.isEmpty())
			return objList;
		
		
		try {
			return insertManyTransactional(objList);
		} catch (Exception e) {
			throw new DuplicateDataEx(String.format("Exception When bulk inserting %s", entity()), e);
		}
	}

	@Transactional
	default Collection<DAO> insertManyTransactional(Collection<DAO> objList){
		return mongoTemplate().insert(objList, this.tableName());
	}
	
	default DAO update(DAO obj) throws DataNotFoundEx {
		DAO dbObj = this.findById(obj.primaryKeyValue());
		if (!obj.equals(dbObj)) {
			return this.save(obj);
		}
		return dbObj;
	}

	// default DAO upsert(DAO obj) {
	// if (this.exists(obj)) {
	// return this.save(obj);
	// } else {
	// return this.insert(obj);
	// }
	// }

	default DAO delete(DAO obj) throws DataNotFoundEx {
		return this.deleteById(obj.primaryKeyValue());
	}

	default void deleteMultiple(Collection<DAO> objList) {
		this.deleteByIds(objList.stream()
				.map(obj -> obj.primaryKeyValue())
				.collect(Collectors.toSet()));
	}

	default DAO deleteById(PK key) throws DataNotFoundEx {
		DAO obj = this.findById(key);
		this.deleteWith(Query.query(Criteria.where("_id").is(key)));
		return obj;
	}

	default void deleteByIds(Collection<PK> keys) {
		this.deleteWith(Query.query(Criteria.where("_id").in(keys)));
	}

//	default void deleteAll() {
//		mongoTemplate().dropCollection(this.tableName());
//		mongoTemplate().createCollection(this.tableName());
//	}

}
