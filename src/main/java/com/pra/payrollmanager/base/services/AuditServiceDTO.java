package com.pra.payrollmanager.base.services;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.pra.payrollmanager.base.dal.BaseAuditDAL;
import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.base.data.BaseAuditDTO;

abstract public class AuditServiceDTO<PK,
		DAO extends BaseAuditDAO<PK>,
		DTO extends BaseAuditDTO<DAO>,
		DAL extends BaseAuditDAL<PK, DAO>>
		extends ServiceDTO<PK, DAO, DTO, DAL> implements BaseAuditServiceDTO<PK, DAO, DTO, DAL> {

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
