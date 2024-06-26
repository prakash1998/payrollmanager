package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.StaleDataEx;

public interface BaseAuditDAL<PK, DAO extends BaseAuditDAO<PK>> extends BaseDAL<PK, DAO>, AuditSupport<PK, DAO> {

	default String auditTableName() {
		return this.tableName().concat(AUDIT_POSTFIX);
	}

	default List<DAO> findWithoutAuditInfo(Query query) {
		return BaseDAL.super.findWith(query, "modifier", "modifiedDate", "createdBy", "createdDate");
	}

	@Override
	default DAO create(DAO obj) {
		return BaseDAL.super.create(setAuditInfoOnCreate(obj));
	}

	// @Override
	// default Collection<DAO> insert(Collection<DAO> objList) {
	// return BaseDAL.super.insert(objList.stream()
	// .map(obj -> setAuditInfoOnCreate(obj))
	// .collect(Collectors.toList()));
	// }

	default boolean modificationValid(DAO dbObj, DAO objToSave) {
		return dbObj.getModifiedDate().equals(objToSave.getModifiedDate());
	}

	@Override
	default DAO update(DAO obj) throws DataNotFoundEx {
		DAO dbObj = this.findById(obj.primaryKeyValue());

		if (obj.getModifiedDate() == null || (dbObj.getModifiedDate() == null || !modificationValid(dbObj, obj))) {
			throw new StaleDataEx(String.format("Data became stale for : %s , Please refresh data", entity().name()));
		}

		if (!obj.equals(dbObj)) {
			audit(dbObj);
			return BaseDAL.super.update(setAuditInfoOnUpdate(dbObj, obj));
		}
		return dbObj;
	}

	@Override
	@Transactional
	default Collection<DAO> deleteWith(Query query) {
		Collection<DAO> deletedObjs = BaseDAL.super.deleteWith(query);
		auditDeleted(deletedObjs);
		return deletedObjs;
	}

}
