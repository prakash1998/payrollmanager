package com.pra.payrollmanager.base.dal.next;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.base.services.next.ApiRestriction;
import com.pra.payrollmanager.security.authorization.permission.api.ApiServices;
import com.pra.payrollmanager.translation.JsonJacksonMapperService;

public interface AuditingService<PK, DAO extends BaseAuditDAO<PK>> extends ApiRestriction{

	MongoTemplate mongoTemplate();

	JsonJacksonMapperService mapper();

	String auditTableName();

	default boolean auditingEnabled() {
		return isAllowedFor(ApiServices.AUDIT);
	};

	default void auditDeleted(DAO obj) {
		obj.setDeleted(true);
		audit(obj);
	}

	default void audit(DAO obj) {
		if (auditingEnabled()) {
			String jsonString = mapper().objectToJson(obj);
			Document doc = Document.parse(jsonString);
			mongoTemplate().insert(doc, auditTableName());
		}
	}

}
