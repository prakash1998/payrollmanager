package com.pra.payrollmanager.base.dal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.translation.JsonJacksonMapperService;

public abstract class BaseAuditDAL<PK, DAO extends BaseAuditDAO<PK>>
		implements DataStoreService<PK, DAO>, AuditingService<PK, DAO> {

	public static final String AUDIT_POSTFIX = "_ODT";

	@Override
	public String auditTableName() {
		return this.tableName().concat(AUDIT_POSTFIX);
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private JsonJacksonMapperService mapper;

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
	public JsonJacksonMapperService mapper() {
		return mapper;
	}

	@Override
	public AuthorityService authorityService() {
		return authorityService;
	}

	@Override
	public String tableName() {
		return this.entity().table();
	}

	@Override
	public DAO insert(DAO obj) {
		DataStoreService.super.insert(injectCreationInfo(obj));
		return obj;
	}

	@Override
	public DAO update(DAO obj) throws DataNotFoundEx {
		DAO dbObj = this.findById(obj.primaryKeyValue());
		if (!dbObj.getModifiedDate().equals(obj.getModifiedDate()))
			throw new RuntimeException("Data is modified...");
		if (!obj.equals(dbObj)) {
			DataStoreService.super.save(injectModificationInfo(dbObj, obj));
			audit(dbObj);
		}
		return obj;
	}

	@Override
	public List<DAO> deleteWith(Query query) {
		List<DAO> deletedObjs = DataStoreService.super.deleteWith(query);
		auditDeleted(deletedObjs);
		return deletedObjs;
	}

}
