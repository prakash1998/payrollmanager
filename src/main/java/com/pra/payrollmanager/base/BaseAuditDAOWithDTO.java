package com.pra.payrollmanager.base;

import lombok.Getter;

@Getter
abstract public class BaseAuditDAOWithDTO<PK, DTO extends BaseAuditDTO<?>> extends BaseAuditDAO<PK>
		implements BaseDAOWithDTO<PK, DTO> {

	abstract protected DTO toDto();

	final public DTO toDTO() {
		DTO dto = this.toDto();
		dto.setModifier(super.getModifier());
		dto.setModifiedDate(super.getModifiedDate());
		return dto;
	}
}
