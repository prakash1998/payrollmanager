package com.pra.payrollmanager.base.dal;

import java.util.Collection;

import org.springframework.data.mongodb.core.query.Query;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.utils.ObjectUtils;

public interface BaseRestrictedAuditDAL<PK, DAO extends BaseAuditDAO<PK>>
		extends BaseRestrictedDAL<PK, DAO>, BaseAuditDAL<PK, DAO> {

	@Override
	default DAO create(DAO obj) {
		validateItemAccess(setAuditInfoOnCreate(ObjectUtils.copyOf(obj)));
		return BaseAuditDAL.super.create(obj);
	}

//	@Override
//	default Collection<DAO> insert(Collection<DAO> objList) {
//		validateItemAccess(objList.stream()
//				.map(obj -> setAuditInfoOnCreate(BeanUtils.copyOf(obj)))
//				.collect(Collectors.toList()));
//		return BaseAuditDAL.super.insert(objList);
//	}

	@Override
	default void validateBeforeUpdate(DAO dbObj, DAO obj) {
		validateItemAccess(obj);
		BaseAuditDAL.super.validateBeforeUpdate(dbObj, obj);
	}

	@Override
	default DAO update(DAO obj) throws DataNotFoundEx {
		validateItemAccess(obj);
		return BaseAuditDAL.super.update(obj);
	}

	@Override
	default Collection<DAO> deleteWith(Query query) {
		validateDelete(query);
		return BaseAuditDAL.super.deleteWith(query);
	}

}
