package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.result.UpdateResult;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.base.data.BulkOp;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.CustomExceptions;
import com.pra.payrollmanager.utils.DBQueryUtils;

public interface BaseDAL<PK, DAO extends BaseDAO<PK>> extends MongoTableOps<PK, DAO> {

	default boolean existsById(PK key) {
		return MongoTableOps.super._existsById(key);
	}

	default List<DAO> findAll() {
		return MongoTableOps.super._findAll();
	}

	default List<DAO> findAll(String... selectionFields) {
		return this.findWith(new Query(), selectionFields);
	}

	default List<DAO> findWith(Query query) {
		return MongoTableOps.super._find(query);
	}

	default List<DAO> findWith(Query query, String... selectionFields) {
		Field mongoField = query.fields();
		for (String field : selectionFields) {
			mongoField = mongoField.exclude(field);
		}
		return MongoTableOps.super._find(query);
	}

	default DAO findOneWith(Query query) {
		return MongoTableOps.super._findOne(query);
	}

	default Collection<DAO> deleteWith(Query query) {
		return MongoTableOps.super._findAllAndRemove(query);
	}

	@Transactional
	default BulkOp<DAO> bulkOp(BulkOp<DAO> bulkOp) {
		Collection<DAO> removedItems = bulkOp.getRemoved();

		BulkOp.BulkOpBuilder<DAO> dataBuilder = BulkOp.builder();
		if (!removedItems.isEmpty()) {
			Collection<DAO> removed = this.deleteByIds(removedItems.stream()
					.map(item -> item.primaryKeyValue())
					.collect(Collectors.toList()));
			dataBuilder = dataBuilder.removed(removed);
		}

		Collection<DAO> updatedItems = bulkOp.getUpdated();
		if (!updatedItems.isEmpty()) {
			Collection<DAO> updated = MongoTableOps.super._insert(updatedItems);
			dataBuilder = dataBuilder.removed(updated);
		}

		Collection<DAO> addedItems = bulkOp.getAdded();
		if (!addedItems.isEmpty()) {
			Collection<DAO> added = MongoTableOps.super._insert(addedItems);
			dataBuilder = dataBuilder.added(added);
		}

		return dataBuilder.build();
	}

	default boolean exists(DAO obj) {
		return this.existsById(obj.primaryKeyValue());
	}

	default List<DAO> findByIds(Set<PK> keyList) {
		return this.findWith(DBQueryUtils.idInArrayQuery(keyList.toArray()));
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
			return MongoTableOps.super._insert(obj);
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

	default void validateBeforeUpdate(DAO dbObj, DAO obj) {

	}

	default DAO update(DAO obj) throws DataNotFoundEx {
		DAO dbObj = this.findById(obj.primaryKeyValue());
		validateBeforeUpdate(dbObj, obj);
		if (!obj.equals(dbObj)) {
			return MongoTableOps.super._save(obj);
		}
		return dbObj;
	}

	default UpdateResult applyPatch(PK key, Update update) throws DataNotFoundEx {
		return MongoTableOps.super._updateFirst(DBQueryUtils.idEqualsQuery(key), update);
	}

	default DAO applyPatchAndGet(PK key, Update update) throws DataNotFoundEx {
		this.applyPatch(key, update);
		return findById(key);
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
		Collection<DAO> deleted = this.deleteWith(DBQueryUtils.idEqualsQuery(key));
		return deleted.stream().findFirst()
				.orElseThrow(() -> CustomExceptions.notFoundEx(entity(), String.valueOf(key)));
	}

	default Collection<DAO> deleteByIds(Collection<PK> keys) {
		return this.deleteWith(DBQueryUtils.idInArrayQuery(keys.toArray()));
	}

}
