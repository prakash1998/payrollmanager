package com.pra.payrollmanager.base;

import java.time.Instant;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class BaseAuditDAO<PK> implements BaseDAO<PK> {

	@LastModifiedBy
	private String modifier;
	@LastModifiedDate
	private Instant modifiedDate;

	@Transient
	private boolean deleted;

	// public <T extends BaseDAO> T withModificationInfo(String modifier,
	// LocalDateTime modifiedDate){
	// this.modifier = modifier;
	// this.modifiedDate = modifiedDate;
	// return (T)this;
	// }
}
