package com.pra.payrollmanager.base.dal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.security.authorization.AuthorityService;

public abstract class AuditDAL<PK, DAO extends BaseAuditDAO<PK>>
		implements BaseDAL<PK, DAO>, AuditingHelper<PK, DAO> {

	public static final String AUDIT_POSTFIX = "_ODT";

	@Override
	public String auditTableName() {
		return this.tableName().concat(AUDIT_POSTFIX);
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	protected AuthorityService authorityService;

	@SuppressWarnings("unchecked")
	@Override
	public Class<DAO> daoClazz() {
		// specified in each class in hierarchy because we can access type parameter
		// class in immediate parent only
		Type sooper = getClass().getGenericSuperclass();
		return (Class<DAO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[1];
	}

	@Override
	public MongoTemplate mongoTemplate() {
		return mongoTemplate;
	}

	@Override
	public AuthorityService authorityService() {
		return authorityService;
	}

	@Override
	public String tableName() {
		return this.entity().table();
	}

	protected DAO injectAuditInfoOnUpdate(DAO dbObj, DAO obj) {
		obj.setCreatedBy(dbObj.getCreatedBy());
		obj.setCreatedDate(dbObj.getCreatedDate());
		obj.setModifier(user());
		obj.setModifiedDate(Instant.now());
		return obj;
	}

	protected DAO injectAuditInfoOnCreate(DAO obj) {
		Instant now = Instant.now();
		obj.setCreatedBy(user());
		obj.setCreatedDate(now);
		obj.setModifier(user());
		obj.setModifiedDate(now);
		return obj;
	}

	@Override
	public DAO insert(DAO obj) {
		return BaseDAL.super.insert(injectAuditInfoOnCreate(obj));
	}

	@Override
	public DAO update(DAO obj) throws DataNotFoundEx {
		DAO dbObj = this.findById(obj.primaryKeyValue());
		if (dbObj.getModifiedDate() != null && !dbObj.getModifiedDate().equals(obj.getModifiedDate()))
			throw new RuntimeException("Data is modified...");
		if (!obj.equals(dbObj)) {
			audit(dbObj);
			return BaseDAL.super.save(injectAuditInfoOnUpdate(dbObj, obj));
		}
		return dbObj;
	}

	@Override
	public List<DAO> deleteWith(Query query) {
		List<DAO> deletedObjs = BaseDAL.super.deleteWith(query);
		auditDeleted(deletedObjs);
		return deletedObjs;
	}

}
