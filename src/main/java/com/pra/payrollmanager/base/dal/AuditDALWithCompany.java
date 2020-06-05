package com.pra.payrollmanager.base.dal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.pra.payrollmanager.base.data.BaseAuditDAO;

abstract public class AuditDALWithCompany<PK, DAO extends BaseAuditDAO<PK>>
		extends AuditDAL<PK, DAO> implements WithTablePrefix{
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<DAO> daoClazz() {
		// specified in each class in hierarchy because we can access type parameter
		// class in immediate parent only
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
		return WithTablePrefix.super.companyPrefix(authorityService) + this.entity().table();
	}
	
	
	@Override
	public String user() {
		return authorityService.getUserName();
	}
}
