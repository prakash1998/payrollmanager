package com.pra.payrollmanager.entity;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.base.dal.BaseDAL;

public class EntityUtils {

	public static void createTableForEntities(MongoTemplate mongoTemplate, String prefix,
			Class<? extends EntityName> entityEnum) {
		EntityName[] tables = entityEnum.getEnumConstants();
		for (EntityName entity : tables) {
			String collectionName = prefix + entity.table();

			if (!mongoTemplate.collectionExists(collectionName))
				mongoTemplate.createCollection(collectionName);

			if (entity.withAudit()) {
				String auditCollectionName = collectionName + AuditDAL.AUDIT_POSTFIX;
				if (!mongoTemplate.collectionExists(auditCollectionName))
					mongoTemplate.createCollection(auditCollectionName);
			}
		}
	}

	public static void createTableForCompanyEntities(MongoTemplate mongoTemplate, String companyPrefix) {
		createTableForEntities(mongoTemplate, companyPrefix,
				CompanyEntityNames.class);
	}

	public static void createTableForCommonEntities(BaseDAL<?, ?> dataAccessLayer) {
		createTableForEntities(dataAccessLayer.mongoTemplate(), dataAccessLayer.commonTablePrefix(),
				CommonEntityNames.class);
	}

}
