package com.pra.payrollmanager.entity;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.base.dal.DALWithCommon;

public class EntityUtils {

	public static void createTableForEntities(MongoTemplate mongoTemplate, String prefix,
			Class<? extends EntityName> entityEnum) {
		EntityName[] tables = entityEnum.getEnumConstants();
		for (EntityName entity : tables) {
			String collectionName = prefix + entity.table();
			if (!mongoTemplate.collectionExists(collectionName))
				mongoTemplate.createCollection(collectionName);
		}
	}

	public static void createTableForCompanyEntities(MongoTemplate mongoTemplate, String companyPrefix) {
		createTableForEntities(mongoTemplate, companyPrefix,
				CompanyEntityNames.class);
	}

	public static void createTableForCommonEntities(DALWithCommon<?, ?> dataAccessLayer) {
		createTableForEntities(dataAccessLayer.mongoTemplate(), dataAccessLayer.commonPrefix(),
				CommonEntityNames.class);
	}

}
