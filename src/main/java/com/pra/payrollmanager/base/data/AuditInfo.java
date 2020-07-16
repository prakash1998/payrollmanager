package com.pra.payrollmanager.base.data;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import com.pra.payrollmanager.exception.unchecked.AppException;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class AuditInfo implements Serializable {

	public static final String CREATED_BY_FIELD = "AcB";

	public static final String CREATED_DATE_FIELD = "AcD";

	public static final String MODIFIER_FIELD = "AmB";

	public static final String MODIFIED_DATE_FIELD = "AmD";

	public static final String DELETED_BY_FIELD = "AdB";

	public static final String DELETED_DATE_FIELD = "AdD";

	/**
	 * 
	 */
	private static final long serialVersionUID = -7745970477952693020L;

	@ApiModelProperty(hidden = true)
	@Field(CREATED_BY_FIELD)
	private String createdBy;

	@ApiModelProperty(hidden = true)
	@Field(CREATED_DATE_FIELD)
	private Instant createdDate;

	@ApiModelProperty(hidden = true)
	@Field(MODIFIER_FIELD)
	private String modifier;
	@ApiModelProperty(hidden = true)
	@Field(MODIFIED_DATE_FIELD)
	private Instant modifiedDate;

	@Transient
	@ApiModelProperty(hidden = true)
	@Field(DELETED_BY_FIELD)
	private String deletedBy;

	@Transient
	@ApiModelProperty(hidden = true)
	@Field(DELETED_DATE_FIELD)
	private Instant deletedDate;

	@SuppressWarnings("unchecked")
	public <T extends AuditInfo> T copyAuditInfoFrom(AuditInfo obj, Class<T> castTo) {
		this.createdBy = obj.getCreatedBy();
		this.createdDate = obj.getCreatedDate();
		this.modifier = obj.getModifier();
		this.modifiedDate = obj.getModifiedDate();
		this.deletedBy = obj.getDeletedBy();
		this.deletedDate = obj.getDeletedDate();
		if (this.getClass().equals(castTo)) {
			return (T) this;
		} else {
			throw new AppException("tried to cast to wrong object in AuditInfo while copying.");
		}
	}

}
