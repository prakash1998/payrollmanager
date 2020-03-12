package com.pra.payrollmanager.security.authorization.permission;

import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.BaseServiceDTO;

@Service
public class SecurityPermissionService
		extends BaseServiceDTO<String, SecurityPermission, SecurityPermissionDTO, SecurityPermissionDAL> {

}
