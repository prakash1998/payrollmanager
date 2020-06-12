package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.base.data.BulkOp;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.CustomExceptions;
import com.pra.payrollmanager.utils.DBQueryUtils;

public interface BaseDAL<PK, DAO extends BaseDAO<PK>> extends WithDBTable {

	MongoTemplate mongoTemplate();

	Class<DAO> daoClazz();

	// primary
	default boolean existsById(PK key) {
		return mongoTemplate().exists(DBQueryUtils.idEqualsQuery(key),
				daoClazz(), this.tableName());
	}

	// primary
	default List<DAO> findAll() {
		return mongoTemplate().findAll(daoClazz(), this.tableName());
	}

	// primary
	default List<DAO> findWith(Query query) {
		return mongoTemplate().find(query, daoClazz(), this.tableName());
	}

	// primary
	default DAO findOneWith(Query query) {
		return mongoTemplate().findOne(query, daoClazz(), this.tableName());
	}

	// primary
	default DAO insert(DAO obj) {
		return mongoTemplate().insert(obj, this.tableName());
		// return obj;
	}

	// primary
	default DAO save(DAO obj) {
		return mongoTemplate().save(obj, this.tableName());
		// return obj;
	}

	// primary
	@Transactional
	default List<DAO> deleteWith(Query query) {
		return mongoTemplate().findAllAndRemove(query, this.tableName());
	}

	// primary
	@Transactional
	default Collection<DAO> insertMulti(Collection<DAO> objList) {
		return mongoTemplate().insert(objList, this.tableName());
	}

	@Transactional
	default BulkOp<DAO> bulkOperation(BulkOp<DAO> bulkOp) {
		Collection<DAO> removedItems = bulkOp.getRemoved();
		BulkOp.BulkOpBuilder<DAO> dataBuilder = BulkOp.builder();
		if (removedItems.isEmpty()) {
			Collection<DAO> removed = this.deleteByIds(removedItems.stream()
					.map(item -> item.primaryKeyValue())
					.collect(Collectors.toList()));
			dataBuilder = dataBuilder.removed(removed);
		}

		Collection<DAO> updatedItems = bulkOp.getUpdated();
		if (updatedItems.isEmpty()) {
			Collection<DAO> updated = this.insertMulti(updatedItems);
			dataBuilder = dataBuilder.removed(updated);
		}

		Collection<DAO> addedItems = bulkOp.getAdded();
		if (addedItems.isEmpty()) {
			Collection<DAO> added = this.insertMulti(addedItems);
			dataBuilder = dataBuilder.added(added);
		}

		return dataBuilder.build();
	}

	default boolean exists(DAO obj) {
		return this.existsById(obj.primaryKeyValue());
	}

	default List<DAO> findByIds(Set<PK> keyList) {
		return findWith(DBQueryUtils.idInQuery(keyList));
	}

	default DAO findByIdUnchecked(PK key) {
		return this.findOneWith(DBQueryUtils.idEqualsQuery(key));
	}

	default DAO findById(PK key) throws DataNotFoundEx {
		DAO dbObj = this.findByIdUnchecked(key);
		if (dbObj != null) {
			return dbObj;
		} else {
			throw CustomExceptions.notFoundEx(entity(), String.valueOf(key));
		}
	}

	default DAO create(DAO obj) throws DuplicateDataEx {
		if (this.exists(obj)) {
			throw CustomExceptions.duplicateEx(entity(), String.valueOf(obj.primaryKeyValue()));
		} else {
			return this.insert(obj);
			// return obj;
		}
	}

	// default Collection<DAO> createMultiple(Collection<DAO> objList) throws
	// DuplicateDataEx {
	// if (objList.isEmpty())
	// return objList;
	//
	// try {
	// return multiInsert(objList);
	// } catch (Exception e) {
	// throw new DuplicateDataEx(String.format("Exception When bulk inserting %s",
	// entity()), e);
	// }
	// }

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

	default DAO deleteById(PK key) throws DataNotFoundEx {
		DAO obj = this.findById(key);
		this.deleteWith(DBQueryUtils.idEqualsQuery(key));
		return obj;
	}

	default Collection<DAO> deleteByIds(Collection<PK> keys) {
		return this.deleteWith(DBQueryUtils.idInQuery(keys));
	}

	// default void deleteAll() {
	// mongoTemplate().dropCollection(this.tableName());
	// mongoTemplate().createCollection(this.tableName());
	// }

}
