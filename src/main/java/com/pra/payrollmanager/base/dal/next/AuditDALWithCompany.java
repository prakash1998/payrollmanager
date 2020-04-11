package com.pra.payrollmanager.base.dal.next;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.security.authorization.AuthorityService;

abstract public class AuditDALWithCompany<PK, DAO extends BaseAuditDAO<PK>>
		extends BaseAuditDAL<PK, DAO> {

	@Autowired
	private AuthorityService authorityService;

	@SuppressWarnings("unchecked")
	@Override
	public Class<DAO> daoClazz() {
		Type sooper = getClass().getGenericSuperclass();
		return (Class<DAO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[1];
	}

	// CAUSION : don't write any logic here
	// this class is created, so that anyone do not miss to
	// specify table name if they want to prevent using
	// company prefix

	@Override
	public String tableName() {
		return authorityService.getTablePrefix() + this.entity().table();
	}

}
