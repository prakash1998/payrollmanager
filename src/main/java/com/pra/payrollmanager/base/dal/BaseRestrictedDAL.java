package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.util.CustomExceptions;

public interface BaseRestrictedDAL<PK, DAO extends BaseDAO<PK>> extends BaseDAL<PK, DAO>, DataRestrictionSupport {

	default Criteria findAllAccessCriteria() {
		return null;
	}

	default Predicate<DAO> hasAccessToItem() {
		return null;
	}

	default void validateItemAccess(DAO item) {
		Predicate<DAO> hasAccessToItem = hasAccessToItem();
		if (hasAccessToItem == null)
			return;

		if (!hasAccessToItem.test(item))
			throw CustomExceptions.notAuthorizedEx(entity(),
					item.primaryKeyValue().toString());
	}

	default void validateItemAccess(Collection<DAO> items) {
		Predicate<DAO> hasAccessToItem = hasAccessToItem();
		if (hasAccessToItem == null)
			return;

		for (DAO item : items) {
			if (!hasAccessToItem.test(item))
				throw CustomExceptions.notAuthorizedEx(entity(),
						item.primaryKeyValue().toString());
		}
	}

	default void validateDelete(Query query) {
		List<DAO> dbObjs = BaseDAL.super.findWith(query);
		validateItemAccess(dbObjs);
	}

	@Override
	default List<DAO> findAll() {

		Criteria findAccessCriteria = findAllAccessCriteria();
		Predicate<DAO> hasAccessToItem = hasAccessToItem();

		if (findAccessCriteria == null) {
			List<DAO> allData = BaseDAL.super.findAll();
			if (hasAccessToItem == null) {
				return allData;
			}
			return allData.stream()
					.filter(hasAccessToItem)
					.collect(Collectors.toList());
		}

		List<DAO> filteredByCriteria = BaseDAL.super.findWith(Query.query(findAccessCriteria));
		if (hasAccessToItem == null) {
			return filteredByCriteria;
		}

		return filteredByCriteria.stream()
				.filter(hasAccessToItem)
				.collect(Collectors.toList());
	}

	@Override
	default List<DAO> findWith(Query query) {
		Criteria findAccessCriteria = findAllAccessCriteria();
		Predicate<DAO> hasAccessToItem = hasAccessToItem();

		if (findAccessCriteria != null)
			query = query.addCriteria(findAccessCriteria);

		List<DAO> filteredByCriteria = BaseDAL.super.findWith(query);

		if (hasAccessToItem == null) {
			return filteredByCriteria;
		}

		return filteredByCriteria.stream()
				.filter(hasAccessToItem)
				.collect(Collectors.toList());
	}

	@Override
	default DAO findOneWith(Query query) {
		DAO dbObj = BaseDAL.super.findOneWith(query);
		validateItemAccess(dbObj);
		return dbObj;
	}

	@Override
	default DAO findById(PK key) throws DataNotFoundEx {
		DAO dbObj = BaseDAL.super.findById(key);
		validateItemAccess(dbObj);
		return dbObj;
	}

	@Override
	default DAO create(DAO obj) {
		validateItemAccess(obj);
		return BaseDAL.super.create(obj);
	}

	@Override
	default DAO update(DAO obj) {
		validateItemAccess(obj);
		return BaseDAL.super.update(obj);
	}

	@Override
	default Collection<DAO> deleteWith(Query query) {
		validateDelete(query);
		return BaseDAL.super.deleteWith(query);
	}

	@Override
	default Collection<DAO> insert(Collection<DAO> objList) {
		validateItemAccess(objList);
		return BaseDAL.super.insert(objList);
	}

}
