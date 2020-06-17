package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Query;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;

public interface BaseRestrictedAuditDAL<PK, DAO extends BaseAuditDAO<PK>>
		extends BaseAuditDAL<PK, DAO>, BaseRestrictedDAL<PK, DAO> {

	@Override
	default DAO create(DAO obj) {
		validateItemAccess(setAuditInfoOnCreate(obj));
		return BaseAuditDAL.super.create(obj);
	}

	@Override
	default Collection<DAO> insert(Collection<DAO> objList) {
		validateItemAccess(objList.stream()
				.map(obj -> setAuditInfoOnCreate(obj))
				.collect(Collectors.toList()));
		return BaseAuditDAL.super.insert(objList);
	}

	@Override
	default void validateOnUpdate(DAO dbObj, DAO obj) {
		validateItemAccess(obj);
		BaseAuditDAL.super.validateOnUpdate(dbObj, obj);
	}

	@Override
	default DAO update(DAO obj) throws DataNotFoundEx {
		return BaseAuditDAL.super.update(obj);
	}

	@Override
	default Collection<DAO> deleteWith(Query query) {
		validateDelete(query);
		return BaseAuditDAL.super.deleteWith(query);
	}

}
