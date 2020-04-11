package com.pra.payrollmanager.security.authorization.permission;

import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.next.AuditRTServiceDTO;

@Service
public class SecurityPermissionService
		extends AuditRTServiceDTO<String, SecurityPermission, SecurityPermissionDTO, SecurityPermissionDAL> {

	@Override
	public String mqTopic() {
		return null;
	}

}
