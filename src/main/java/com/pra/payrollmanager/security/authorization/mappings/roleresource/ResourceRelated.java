package com.pra.payrollmanager.security.authorization.mappings.roleresource;

import org.bson.types.ObjectId;

import com.pra.payrollmanager.base.data.BaseDAO;

public interface ResourceRelated extends BaseDAO<ObjectId> {

	ObjectId resourceId();

}
