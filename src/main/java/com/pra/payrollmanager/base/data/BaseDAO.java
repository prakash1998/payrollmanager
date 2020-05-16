package com.pra.payrollmanager.base.data;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document
public interface BaseDAO<PK> extends Serializable{
	public PK primaryKeyValue();
}
