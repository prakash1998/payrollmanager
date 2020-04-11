package com.pra.payrollmanager.base.services.next;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.BaseAuditDAOWithDTO;
import com.pra.payrollmanager.base.BaseAuditDTO;
import com.pra.payrollmanager.base.dal.next.BaseAuditDAL;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;

abstract public class AuditRTServiceDTO<PK,
		DAO extends BaseAuditDAOWithDTO<PK, DTO>,
		DTO extends BaseAuditDTO<DAO>,
		DAL extends BaseAuditDAL<PK, DAO>>
		extends RTServiceDTO<PK, DAO, DTO, DAL> {

}
