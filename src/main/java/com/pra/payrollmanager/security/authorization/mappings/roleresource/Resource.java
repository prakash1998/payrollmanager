package com.pra.payrollmanager.security.authorization.mappings.roleresource;

import org.bson.types.ObjectId;

import com.pra.payrollmanager.base.data.BaseDAO;

public interface Resource extends BaseDAO<ObjectId> {

	default ObjectId resourceId() {
		return primaryKeyValue();
	}

	ResourceType resourceType();

	String display();

}
