package com.pra.payrollmanager.base.data;

import java.time.Instant;

import org.springframework.data.annotation.Transient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class AuditInfo {

	@ApiModelProperty(hidden = true) 
	private String createdBy;
	@ApiModelProperty(hidden = true) 
	private Instant createdDate;
	@ApiModelProperty(hidden = true) 
	private String modifier;
	@ApiModelProperty(hidden = true) 
	private Instant modifiedDate;
	
	@Transient
	@ApiModelProperty(hidden = true) 
	private Boolean deleted;
	
	@Transient
	@ApiModelProperty(hidden = true) 
	private String deletedBy;
	
	public <T extends AuditInfo> T copyAuditInfoFrom(AuditInfo obj,Class<T> castTo) {
		this.createdBy = obj.getCreatedBy();
		this.createdDate = obj.getCreatedDate();
		this.modifier = obj.getModifier();
		this.modifiedDate = obj.getModifiedDate();
		this.deleted = obj.getDeleted();
		this.deletedBy = obj.getDeletedBy();
		return (T) this;
	}

}
