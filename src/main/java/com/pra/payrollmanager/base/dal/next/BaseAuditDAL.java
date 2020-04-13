package com.pra.payrollmanager.base.dal.next;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.security.authorization.permission.api.ApiServices;
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
	public DAO update(DAO obj) throws DataNotFoundEx {
		DAO dbObj = DataStoreService.super.update(obj);
		if (!dbObj.equals(obj)) {
			audit(dbObj);
		}
		return dbObj;
	}

	@Override
	public DAO delete(DAO obj) throws DataNotFoundEx {
		DAO deletedObj = DataStoreService.super.delete(obj);
		auditDeleted(deletedObj);
		return deletedObj;
	}

	@Override
	public DAO deleteById(PK key) throws DataNotFoundEx {
		DAO deletedObj = DataStoreService.super.deleteById(key);
		auditDeleted(deletedObj);
		return deletedObj;
	}

}
