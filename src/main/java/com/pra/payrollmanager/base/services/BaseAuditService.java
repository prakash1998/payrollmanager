package com.pra.payrollmanager.base.services;

import java.util.List;

import com.pra.payrollmanager.base.data.AuditInfo;

public interface BaseAuditService<PK, DATA extends AuditInfo> {

//	boolean existsInAudit(DATA obj);

	List<DATA> getAllFromAudit();

//	DATA getByIdFromAudit(PK id) throws DataNotFoundEx, AnyThrowable;
//
//	List<DATA> getByIdsFromAudit(Set<PK> id) throws DataNotFoundEx, AnyThrowable;
	
	

}
