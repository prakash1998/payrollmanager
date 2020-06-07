package com.pra.payrollmanager.base.services;

import java.util.List;
import java.util.Set;

import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;

public interface BaseAuditService<PK, DATA> {

	boolean existsInAudit(DATA obj);

	List<DATA> getAllFromAudit();

	DATA getByIdFromAudit(PK id) throws DataNotFoundEx, AnyThrowable;

	List<DATA> getByIdsFromAudit(Set<PK> id) throws DataNotFoundEx, AnyThrowable;

}
