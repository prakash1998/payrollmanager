package com.pra.payrollmanager.base.data;

import java.time.Instant;

import io.swagger.annotations.ApiModelProperty;
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

	@ApiModelProperty(hidden = true) 
	private String createdBy;
	@ApiModelProperty(hidden = true) 
	private Instant createdDate;
	@ApiModelProperty(hidden = true) 
	private String modifier;
	@ApiModelProperty(hidden = true) 
	private Instant modifiedDate;
	@ApiModelProperty(hidden = true) 
	private Boolean deleted;
	@ApiModelProperty(hidden = true) 
	private String deletedBy;

	abstract protected DAO toPlainDAO();

	public final DAO toDAO() {
		DAO dao = this.toPlainDAO();
		dao.setModifiedDate(this.modifiedDate);
		return dao;
	}

}
