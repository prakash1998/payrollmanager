package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.result.UpdateResult;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.utils.DBQueryUtils;

public interface MongoTableOps<PK, DAO extends BaseDAO<PK>> extends WithDBTable {

	MongoTemplate mongoTemplate();

	Class<DAO> daoClazz();

	default boolean existsById(PK key) {
		return mongoTemplate().exists(DBQueryUtils.idEqualsQuery(key),
				daoClazz(), this.tableName());
	}

	default List<DAO> findAll() {
		return mongoTemplate().findAll(daoClazz(), this.tableName());
	}

	default List<DAO> find(Query query) {
		return mongoTemplate().find(query, daoClazz(), this.tableName());
	}

	default DAO findOne(Query query) {
		return mongoTemplate().findOne(query, daoClazz(), this.tableName());
	}

	default DAO insert(DAO obj) {
		return mongoTemplate().insert(obj, this.tableName());
	}

	@Transactional
	default Collection<DAO> insert(Collection<DAO> objList) {
		return mongoTemplate().insert(objList, this.tableName());
	}

	default DAO save(DAO obj) {
		return mongoTemplate().save(obj, this.tableName());
	}

	default UpdateResult updateFirst(Query query, Update update) {
		return mongoTemplate().updateFirst(query, update, this.tableName());
	}

	@Transactional
	default UpdateResult updateMulti(Query query, Update update) {
		return mongoTemplate().updateMulti(query, update, this.tableName());
	}

	@Transactional
	default List<DAO> findAllAndRemove(Query query) {
		return mongoTemplate().findAllAndRemove(query, this.tableName());
	}

	default void truncateTable() {
		mongoTemplate().dropCollection(this.tableName());
		mongoTemplate().createCollection(this.tableName());
	}

}
