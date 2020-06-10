package com.pra.payrollmanager.base.services;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;
import com.pra.payrollmanager.base.data.BaseAuditDTO;

abstract public class AuditRTServiceDTO<PK,
		DAO extends BaseAuditDAOWithDTO<PK, DTO>,
		DTO extends BaseAuditDTO<DAO>,
		DAL extends AuditDAL<PK, DAO>>
		extends RTServiceDTO<PK, DAO, DTO, DAL>
		implements BaseAuditServiceDTO<PK, DAO, DTO, DAL>, DALFeaturePermission<DAL> {

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
