package com.pra.payrollmanager.base.dal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.security.authorization.AuthorityService;

abstract public class DALWithCompany<PK, DAO extends BaseDAO<PK>>
		extends AbstractDAL<PK, DAO> implements WithTablePrefix {

	@Autowired
	private AuthorityService authorityService;

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

}
