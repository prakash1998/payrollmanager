package com.pra.payrollmanager.utils.basetest;

import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.audit.BaseServiceAuditDTO;

@Service
public class BaseTestService extends BaseServiceAuditDTO<Integer, BaseTestDAO, BaseTestDTO, BaseTestDAL>{

}
