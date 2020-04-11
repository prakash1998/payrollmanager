package com.pra.payrollmanager.security.authorization.permission.api;

import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.next.AuditServiceDTO;

@Service
public class ApiPermissionService
		extends AuditServiceDTO<String, ApiPermission, ApiPermissionDTO, ApiPermissionDAL> {


}
