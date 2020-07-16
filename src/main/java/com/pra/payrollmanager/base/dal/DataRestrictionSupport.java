package com.pra.payrollmanager.base.dal;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.data.mongodb.core.query.Criteria;

import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.base.services.ApiRestriction;
import com.pra.payrollmanager.entity.EntityName;
import com.pra.payrollmanager.exception.util.CustomExceptions;
import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;

public interface DataRestrictionSupport<PK, DAO extends BaseDAO<PK>> extends ApiRestriction {

	EntityName entity();

	default Criteria findAllAccessCriteria() {
		return null;
	}

	default Predicate<DAO> hasAccessToItem() {
		return null;
	}

	default Function<List<DAO>, List<DAO>> filterItemsWithAccess() {
		return null;
	}

	// default Predicate<PK> hasAccessToItemById() {
	// return null;
	// }

	default boolean accessControlEnabled() {
		if (authorityService().inGodMode())
			return false;
		return isAllowedFor(ApiFeatures.ACCESS_CONTROL);
	}

	// default void validateItemAccess(PK key) {
	//
	// if (!accessControlEnabled())
	// return;
	//
	// Predicate<PK> hasAccessToItemById = hasAccessToItemById();
	// if (hasAccessToItemById == null)
	// return;
	//
	// if (!hasAccessToItemById.test(key))
	// throw CustomExceptions.notAuthorizedEx(entity(),
	// key.toString());
	// }

	default void validateItemAccess(DAO item) {

		if (!accessControlEnabled())
			return;

		Predicate<DAO> hasAccessToItem = hasAccessToItem();
		if (hasAccessToItem == null)
			return;

		if (!hasAccessToItem.test(item))
			throw CustomExceptions.notAuthorizedEx(entity(),
					item.primaryKeyValue().toString());
	}

	default void validateItemAccess(Collection<DAO> items) {

		if (!accessControlEnabled())
			return;

		Predicate<DAO> hasAccessToItem = hasAccessToItem();
		if (hasAccessToItem == null)
			return;

		for (DAO item : items) {
			if (!hasAccessToItem.test(item))
				throw CustomExceptions.notAuthorizedEx(entity(),
						item.primaryKeyValue().toString());
		}
	}

}
