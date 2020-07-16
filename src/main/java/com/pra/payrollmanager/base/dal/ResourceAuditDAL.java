package com.pra.payrollmanager.base.dal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.Resource;
import com.pra.payrollmanager.user.root.permissions.resource.ResourcePermissionDAL;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract public class ResourceAuditDAL<DAO extends BaseAuditDAO<ObjectId> & Resource>
		extends AuditDAL<ObjectId, DAO> implements BaseResourceSupport<DAO> {

	@SuppressWarnings("unchecked")
	@Override
	public Class<DAO> daoClazz() {
		// specified in each class in hierarchy because we can access type parameter
		// class in immediate parent only
		Type sooper = getClass().getGenericSuperclass();
		return (Class<DAO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[0];
	}

	private final ResourcePermissionDAL resourcePermissionDAL;

	@Override
	public ResourcePermissionDAL resourcePermissionDAL() {
		return resourcePermissionDAL;
	}

	@Override
	@Transactional
	public DAO create(DAO obj) throws DuplicateDataEx {
		DAO created = super.create(obj);
		BaseResourceSupport.super.createResource(created);
		return created;
	}

	@Override
	@Transactional
	public DAO update(DAO obj) throws DataNotFoundEx {
		DAO updated = super.update(obj);
		BaseResourceSupport.super.updateResource(updated);
		return updated;
	}

	@Override
	@Transactional
	public Collection<DAO> deleteByIds(Collection<ObjectId> keys) {
		BaseResourceSupport.super.deleteResourcesById(keys);
		return super.deleteByIds(keys);
	}

}
