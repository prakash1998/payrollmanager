package com.pra.payrollmanager.base.data;

import java.time.Instant;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class BaseAuditDAO<PK> implements BaseDAO<PK>, WithAuditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008011887203766983L;

	private String createdBy;
	private Instant createdDate;
	private String modifier;
	private Instant modifiedDate;
	
	@Transient
	private Boolean deleted;
	
	@Transient
	private String deletedBy;

}
