package com.pra.payrollmanager.base.dal;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.base.services.ApiRestriction;
import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;

public interface AuditingHelper<PK, DAO extends BaseAuditDAO<PK>> extends ApiRestriction {

	String REPLACED_ID = "_pk";

	MongoTemplate mongoTemplate();

	Class<DAO> daoClazz();

	String auditTableName();

	String user();

	default boolean auditingEnabled() {
		return ApiRestriction.super.isAllowedFor(ApiFeatures.AUDIT);
	};

	default void clearCreationInfo(DAO obj) {
		// set null because else it will store false
		// by passing we can ignore storing it in db
		obj.setDeleted(null);
		// obj.setDeletedBy(null);
		obj.setCreatedBy(null);
		obj.setCreatedDate(null);
	}

	default DAO injectAuditInfoOnDelete(DAO obj) {
		obj.setDeleted(true);
		obj.setDeletedBy(user());
		return obj;
	}

	default void auditDeleted(DAO obj) {
		if (auditingEnabled()) {
			clearCreationInfo(obj);
			injectAuditInfoOnDelete(obj);
			audit(obj);
		}
	}

	default void auditDeleted(List<DAO> objList) {
		if (auditingEnabled()) {
			objList.forEach(obj -> {
				clearCreationInfo(obj);
				injectAuditInfoOnDelete(obj);
			});
			audit(objList);
		}
	}

	default void audit(DAO obj) {
		if (auditingEnabled()) {
			clearCreationInfo(obj);
			Document doc = objectToAuditDocument(obj);
			mongoTemplate().insert(doc, auditTableName());
		}
	}

	default void audit(List<DAO> objList) {
		if (auditingEnabled()) {
			List<Document> docs = objList.stream()
					.map(obj -> {
						clearCreationInfo(obj);
						return objectToAuditDocument(obj);
					})
					.collect(Collectors.toList());
			mongoTemplate().insert(docs, auditTableName());
		}
	}

	default Document objectToAuditDocument(DAO obj) {
		clearCreationInfo(obj);
		Document documentFromObj = (Document) mongoTemplate().getConverter().convertToMongoType(obj);
		Object keyValue = documentFromObj.remove("_id");
		documentFromObj.put(REPLACED_ID, keyValue);
		return documentFromObj;
	}

	default DAO auditDocumentToObject(Document doc) {
		Object keyValue = doc.remove(REPLACED_ID);
		doc.put("_id", keyValue);
		return mongoTemplate().getConverter().read(daoClazz(), doc);
	}

	default List<DAO> findAllFromAudit() {
		List<Document> documents = mongoTemplate().findAll(Document.class, auditTableName());
		return documents.stream()
				.map(doc -> auditDocumentToObject(doc))
				.collect(Collectors.toList());
	}

}