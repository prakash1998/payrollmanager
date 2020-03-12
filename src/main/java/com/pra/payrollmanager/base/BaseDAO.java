package com.pra.payrollmanager.base;

import javax.persistence.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document
public interface BaseDAO<PK> {
	public PK primaryKeyValue();
}
