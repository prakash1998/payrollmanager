package com.pra.payrollmanager.base.dal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.pra.payrollmanager.base.data.BaseDAO;

public abstract class DALWithCommon<PK, DAO extends BaseDAO<PK>>
		extends BaseDAL<PK, DAO> implements WithTablePrefix {

	@SuppressWarnings("unchecked")
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
		return WithTablePrefix.super.commonPrefix() + this.entity().table();
	}

}
