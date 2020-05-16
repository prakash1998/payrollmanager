package com.pra.payrollmanager.base.dal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.base.data.BaseDAO;

public abstract class BaseDAL<PK, DAO extends BaseDAO<PK>>
		implements DataStoreService<PK, DAO> {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public MongoTemplate mongoTemplate() {
		return mongoTemplate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<DAO> daoClazz() {
		 // specified in each class in hierarchy because we can access type parameter class in immediate parent only 
    Type sooper = getClass().getGenericSuperclass();
		return (Class<DAO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[1];
	}
	
	@Override
	public String tableName() {
		return this.entity().table();
	}

}
