package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;

public interface BaseRestrictedDAL<PK, DAO extends BaseDAO<PK>>
		extends BaseDAL<PK, DAO>, DataRestrictionSupport<PK, DAO> {

	default void validateDelete(Query query) {
		List<DAO> dbObjs = BaseDAL.super.findWith(query);
		validateItemAccess(dbObjs);
	}

	@Override
	default List<DAO> findAll() {

		if (!accessControlEnabled())
			return BaseDAL.super.findAll();

		Criteria findAllAccessCriteria = findAllAccessCriteria();
		Predicate<DAO> hasAccessToItem = hasAccessToItem();
		Function<List<DAO>, List<DAO>> filterItemsWithAccess = filterItemsWithAccess();

		if (findAllAccessCriteria == null) {
			List<DAO> allData = BaseDAL.super.findAll();

			if (filterItemsWithAccess != null) {
				return filterItemsWithAccess.apply(allData);
			}

			if (hasAccessToItem != null) {
				return allData.stream()
						.filter(hasAccessToItem)
						.collect(Collectors.toList());
			}

			return allData;
		}

		List<DAO> filteredByCriteria = BaseDAL.super.findWith(Query.query(findAllAccessCriteria));
		return filteredByCriteria;
		// if (hasAccessToItem == null) {
		// return filteredByCriteria;
		// }
		//
		// return filteredByCriteria.stream()
		// .filter(hasAccessToItem)
		// .collect(Collectors.toList());
	}

	@Override
	default List<DAO> findWith(Query query) {

		if (!accessControlEnabled())
			return BaseDAL.super.findWith(query);

		Criteria findAllAccessCriteria = findAllAccessCriteria();
		Predicate<DAO> hasAccessToItem = hasAccessToItem();
		Function<List<DAO>, List<DAO>> filterItemsWithAccess = filterItemsWithAccess();

		if (findAllAccessCriteria == null) {
			List<DAO> allData = BaseDAL.super.findWith(query);

			if (filterItemsWithAccess != null) {
				return filterItemsWithAccess.apply(allData);
			}

			if (hasAccessToItem != null) {
				return allData.stream()
						.filter(hasAccessToItem)
						.collect(Collectors.toList());
			}

			return allData;
		}

		if (findAllAccessCriteria != null)
			query = query.addCriteria(findAllAccessCriteria);

		List<DAO> filteredByCriteria = BaseDAL.super.findWith(query);

		// if (hasAccessToItem == null) {
		// return filteredByCriteria;
		// }
		//
		// return filteredByCriteria.stream()
		// .filter(hasAccessToItem)
		// .collect(Collectors.toList());

		return filteredByCriteria;
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

	// @Override
	// default Collection<DAO> insert(Collection<DAO> objList) {
	// validateItemAccess(objList);
	// return BaseDAL.super.insert(objList);
	// }

}
