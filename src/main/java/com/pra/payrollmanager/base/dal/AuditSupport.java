package com.pra.payrollmanager.base.dal;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.base.services.ApiRestriction;
import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;

public interface AuditSupport<PK, DAO extends BaseAuditDAO<PK>> extends ApiRestriction, WithUser {

	public static final String REPLACED_ID = "_pk";

	public static final String AUDIT_POSTFIX = "_ODT";
	
	MongoTemplate mongoTemplate();
	
	Class<DAO> daoClazz();
	
	String auditTableName();

	default boolean auditingEnabled() {
		return ApiRestriction.super.isAllowedFor(ApiFeatures.AUDIT);
	};

	default DAO setAuditInfoOnUpdate(DAO dbObj, DAO obj) {
		obj.setCreatedBy(dbObj.getCreatedBy());
		obj.setCreatedDate(dbObj.getCreatedDate());
		obj.setModifier(user());
		obj.setModifiedDate(Instant.now());
		return obj;
	}

	default DAO setAuditInfoOnCreate(DAO obj) {
		Instant now = Instant.now();
		obj.setCreatedBy(user());
		obj.setCreatedDate(now);
		obj.setModifier(user());
		obj.setModifiedDate(now);
		return obj;
	}

	default void clearCreationInfo(DAO obj) {
		// set null because else it will store false
		// by passing we can ignore storing it in db
		obj.setDeleted(null);
		// obj.setDeletedBy(null);
		obj.setCreatedBy(null);
		obj.setCreatedDate(null);
	}

	default DAO setAuditInfoOnDelete(DAO obj) {
		obj.setDeleted(true);
		obj.setDeletedBy(user());
		return obj;
	}

	default void auditDeleted(DAO obj) {
		if (auditingEnabled()) {
			clearCreationInfo(obj);
			setAuditInfoOnDelete(obj);
			audit(obj);
		}
	}

	default void auditDeleted(Collection<DAO> objList) {
		if (auditingEnabled()) {
			objList.forEach(obj -> {
				clearCreationInfo(obj);
				setAuditInfoOnDelete(obj);
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

	default void audit(Collection<DAO> objList) {
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
