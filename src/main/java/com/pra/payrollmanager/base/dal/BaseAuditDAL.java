package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.utils.BeanUtils;

public interface BaseAuditDAL<PK, DAO extends BaseAuditDAO<PK>> extends BaseDAL<PK, DAO>, AuditSupport<PK, DAO> {

	default String auditTableName() {
		return this.tableName().concat(AUDIT_POSTFIX);
	}

	@Override
	default DAO insert(DAO obj) {
		return BaseDAL.super.insert(injectAuditInfoOnCreate(obj));
	}
	
	@Override
	default Collection<DAO> insertMulti(Collection<DAO> objList) {
		return BaseDAL.super.insertMulti(objList.stream()
				.map(obj -> injectAuditInfoOnCreate(obj))
				.collect(Collectors.toList()));
	}

	@Override
	default DAO save(DAO obj) throws DataNotFoundEx {
		DAO dbObj = this.findById(obj.primaryKeyValue());
		if (dbObj.getModifiedDate() != null && !dbObj.getModifiedDate().equals(obj.getModifiedDate()))
			throw new RuntimeException("Data is modified...");
		if (!obj.equals(dbObj)) {
			audit(BeanUtils.copyOf(dbObj));
			return BaseDAL.super.save(injectAuditInfoOnUpdate(dbObj, obj));
		}
		return dbObj;
	}
	
	@Override
	default DAO update(DAO obj) throws DataNotFoundEx {
		return this.save(obj);
	}

	@Override
	@Transactional
	default List<DAO> deleteWith(Query query) {
		List<DAO> deletedObjs = BaseDAL.super.deleteWith(query);
		auditDeleted(deletedObjs);
		return deletedObjs;
	}

}
