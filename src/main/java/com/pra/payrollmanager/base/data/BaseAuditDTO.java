package com.pra.payrollmanager.base.data;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
abstract public class BaseAuditDTO<DAO extends BaseAuditDAO<?>> extends AuditInfo {


//	abstract protected DAO toPlainDAO();
//
//	public final DAO toDAO() {
//		DAO dao = this.toPlainDAO();
//		dao.setModifiedDate(this.modifiedDate);
//		return dao;
//	}

}
