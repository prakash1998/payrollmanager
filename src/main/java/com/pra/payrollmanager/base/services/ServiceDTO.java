package com.pra.payrollmanager.base.services;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.base.data.BaseDTO;
import com.pra.payrollmanager.base.data.WithDTO;

abstract public class ServiceDTO<PK,
		DAO extends BaseDAO<PK> & WithDTO<DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends BaseDAL<PK, DAO>>
		extends ServiceBeans<DAL>
		implements BaseServiceDTO<PK, DAO, DTO, DAL> {

	@SuppressWarnings("unchecked")
	@Override
	public Class<DTO> dtoClazz() {
		// specified in each class in hierarchy because we can access type parameter
		// class in immediate parent only
		Type sooper = getClass().getGenericSuperclass();
		return (Class<DTO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[2];
	}

}
