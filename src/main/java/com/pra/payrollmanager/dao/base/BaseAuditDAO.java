package com.pra.payrollmanager.dao.base;

import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

abstract public class BaseAuditDAO<PK> implements BaseDAO<PK>{

	@LastModifiedBy
	private String modifier;
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	// public <T extends BaseDAO> T withModificationInfo(String modifier,
	// LocalDateTime modifiedDate){
	// this.modifier = modifier;
	// this.modifiedDate = modifiedDate;
	// return (T)this;
	// }
}
