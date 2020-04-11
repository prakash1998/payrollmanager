package com.pra.payrollmanager.base.dal.next;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.translation.JsonJacksonMapperService;

public interface AuditingService<PK, DAO extends BaseAuditDAO<PK>> {

	MongoTemplate mongoTemplate();

	JsonJacksonMapperService mapper();

	String auditTableName();

	default void auditDeleted(DAO obj) {
		obj.setDeleted(true);
		audit(obj);
	}

	default void audit(DAO obj) {
		String jsonString = mapper().objectToJson(obj);
		Document doc = Document.parse(jsonString);
		mongoTemplate().insert(doc, auditTableName());
	}

}
