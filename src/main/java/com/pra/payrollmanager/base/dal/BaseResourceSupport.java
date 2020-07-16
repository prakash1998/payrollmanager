package com.pra.payrollmanager.base.dal;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Update;

import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.Resource;
import com.pra.payrollmanager.user.root.permissions.resource.ResourceDAO;
import com.pra.payrollmanager.user.root.permissions.resource.ResourcePermissionDAL;

public interface BaseResourceSupport<DAO extends Resource>
// extends DataRestrictionSupport<ObjectId, DAO>
{

	ResourcePermissionDAL resourcePermissionDAL();

	// @Override
	// default Criteria findAllAccessCriteria() {
	// return
	// DBQueryUtils.idInCriteria(authorityService().getUserResourceIds().stream().toArray());
	// }
	//
	// @Override
	// default Predicate<DAO> hasAccessToItem() {
	// return item -> authorityService().hasAccessToResourceId(item.resourceId());
	// }

	default DAO createResource(DAO obj) throws DuplicateDataEx {
		resourcePermissionDAL().create(
				ResourceDAO.builder()
						.id(obj.resourceId())
						.type(obj.resourceType())
						.display(obj.display())
						.build());
		return obj;
	}

	default DAO updateResource(DAO obj) throws DataNotFoundEx {
		resourcePermissionDAL().applyPatch(obj.resourceId(), Update.update("display", obj.display()));
		return obj;
	}

	default Collection<ObjectId> deleteResourcesById(Collection<ObjectId> keys) {
		resourcePermissionDAL().deleteByIds(keys);
		return keys;
	}
}
