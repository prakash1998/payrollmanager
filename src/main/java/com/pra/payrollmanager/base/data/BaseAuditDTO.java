package com.pra.payrollmanager.base.data;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class BaseAuditDTO<DAO extends BaseAuditDAOWithDTO<?, ?>>
		implements BaseDTO<DAO>, WithAuditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 962006539916300470L;

	private String createdBy;
	private Instant createdDate;
	private String modifier;
	private Instant modifiedDate;
	private Boolean deleted;
	private String deletedBy;
	
	abstract protected DAO toPlainDAO();

	public final DAO toDAO() {
		DAO dao = this.toPlainDAO();
		dao.setModifiedDate(this.modifiedDate);
		return dao;
	}

}
