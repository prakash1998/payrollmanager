package com.pra.payrollmanager.base.dal;

import java.util.function.Predicate;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;

import com.pra.payrollmanager.security.authorization.mappings.roleresource.ResourceRelated;
import com.pra.payrollmanager.utils.DBQueryUtils;

public interface ResourceRestrictedService<DAO extends ResourceRelated> extends DataRestrictionSupport<ObjectId, DAO> {

	@Override
	default Criteria findAllAccessCriteria() {
		return DBQueryUtils.idInArrayCriteria(authorityService().getUserResourceIds().toArray());
	}

	@Override
	default Predicate<DAO> hasAccessToItem() {
		return item -> authorityService().hasAccessToResourceId(item.resourceId());
	}
	
}
