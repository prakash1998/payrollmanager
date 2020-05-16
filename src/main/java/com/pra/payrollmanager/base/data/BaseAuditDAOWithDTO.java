package com.pra.payrollmanager.base.data;

import lombok.Getter;

@Getter
abstract public class BaseAuditDAOWithDTO<PK, DTO extends BaseAuditDTO<?>>
		extends BaseAuditDAO<PK>
		implements WithDTO<DTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4784217631952563724L;

	abstract protected DTO toPlainDTO();

	public final DTO toDTO() {
		DTO dto = this.toPlainDTO();
		dto.setCreatedBy(super.getCreatedBy());
		dto.setCreatedDate(super.getCreatedDate());
		dto.setModifier(super.getModifier());
		dto.setModifiedDate(super.getModifiedDate());
		dto.setDeleted(super.getDeleted());
		dto.setDeletedBy(super.getDeletedBy());
		return dto;
	}
}
