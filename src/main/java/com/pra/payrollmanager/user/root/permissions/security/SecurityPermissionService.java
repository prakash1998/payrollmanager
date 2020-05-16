package com.pra.payrollmanager.user.root.permissions.security;

import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDTO;

@Service
public class SecurityPermissionService
		extends ServiceDTO<String, SecurityPermission, SecurityPermissionDTO, SecurityPermissionDAL> {


}
