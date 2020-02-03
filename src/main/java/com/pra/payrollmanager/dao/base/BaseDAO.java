package com.pra.payrollmanager.dao.base;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public interface BaseDAO<PK> {
	
	public PK primaryKeyValue();
	
}
