package com.pra.payrollmanager.base.data;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document
@TypeAlias("2")
public interface BaseDAO<PK> extends Serializable{
	public PK primaryKeyValue();
}
