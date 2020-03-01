package com.pra.payrollmanager.base;

import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;

@Getter
abstract public class BaseAuditDAOWithDTO<PK, DTO extends BaseAuditDTO<?>> extends BaseAuditDAO<PK>
		implements BaseDAOWithDTO<PK, DTO> {

	abstract public DTO toDto();

	final public DTO toDTO() {
		DTO dto = this.toDto();
		dto.setModifier(super.getModifier());
		dto.setModifiedDate(super.getModifiedDate());
		return dto;
	}
}
