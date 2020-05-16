package com.pra.payrollmanager.base.dal;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.base.services.ApiRestriction;
import com.pra.payrollmanager.security.authorization.permission.ResourceFeatures;
import com.pra.payrollmanager.translation.JsonJacksonMapperService;

public interface AuditingService<PK, DAO extends BaseAuditDAO<PK>> extends ApiRestriction {

	MongoTemplate mongoTemplate();

	JsonJacksonMapperService mapper();

	String auditTableName();

	String user();

	default boolean auditingEnabled() {
		return ApiRestriction.super.isAllowedFor(ResourceFeatures.AUDIT);
	};

	default DAO injectModificationInfo(DAO dbObj, DAO obj) {
		obj.setCreatedBy(dbObj.getCreatedBy());
		obj.setCreatedDate(dbObj.getCreatedDate());
		obj.setModifier(user());
		obj.setModifiedDate(Instant.now());
		return obj;
	}

	default DAO injectCreationInfo(DAO obj) {
		Instant now = Instant.now();
		obj.setCreatedBy(user());
		obj.setCreatedDate(now);
		obj.setModifier(user());
		obj.setModifiedDate(now);
		return obj;
	}

	default void clearCreationInfo(DAO obj) {
		obj.setDeleted(null);
		obj.setDeletedBy(null);
		obj.setCreatedBy(null);
		obj.setCreatedDate(null);
	}

	default void injectDeleteInfo(DAO obj) {
		obj.setDeleted(true);
		obj.setDeletedBy(user());
	}

	default void auditDeleted(DAO obj) {
		if (auditingEnabled()) {
			clearCreationInfo(obj);
			injectDeleteInfo(obj);
			audit(obj);
		}
	}

	default void auditDeleted(List<DAO> objList) {
		if (auditingEnabled()) {
			objList.forEach(obj -> {
				clearCreationInfo(obj);
				injectDeleteInfo(obj);
			});
			audit(objList);
		}
	}

	default void audit(DAO obj) {
		if (auditingEnabled()) {
			clearCreationInfo(obj);
			String jsonString = mapper().objectToJson(obj);
			Document doc = Document.parse(jsonString);
			mongoTemplate().insert(doc, auditTableName());
		}
	}

	default void audit(List<DAO> objList) {
		if (auditingEnabled()) {
			List<Document> docs = objList.stream()
					.map(obj -> {
						clearCreationInfo(obj);
						return Document.parse(mapper().objectToJson(obj));
					})
					.collect(Collectors.toList());
			mongoTemplate().insert(docs, auditTableName());
		}
	}

}
