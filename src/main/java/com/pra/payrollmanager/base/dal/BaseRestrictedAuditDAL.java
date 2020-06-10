package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Query;

import com.pra.payrollmanager.base.data.BaseAuditDAO;

public interface BaseRestrictedAuditDAL<PK, DAO extends BaseAuditDAO<PK>>
		extends BaseAuditDAL<PK, DAO>, BaseRestrictedDAL<PK, DAO> {

	@Override
	default DAO insert(DAO obj) {
		validateItemAccess(injectAuditInfoOnCreate(obj));
		return BaseAuditDAL.super.insert(obj);
	}

	@Override
	default Collection<DAO> insertMulti(Collection<DAO> objList) {
		validateItemAccess(objList.stream()
				.map(obj -> injectAuditInfoOnCreate(obj))
				.collect(Collectors.toList()));
		return BaseAuditDAL.super.insertMulti(objList);
	}

	@Override
	default DAO save(DAO obj) {
		validateItemAccess(injectAuditInfoOnUpdate(obj, obj));
		return BaseAuditDAL.super.save(obj);
	}

	@Override
	default List<DAO> deleteWith(Query query) {
		validateDelete(query);
		return BaseAuditDAL.super.deleteWith(query);
	}

}
