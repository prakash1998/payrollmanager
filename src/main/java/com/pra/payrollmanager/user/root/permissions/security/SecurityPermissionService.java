package com.pra.payrollmanager.user.root.permissions.security;

import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDAO;

@Service
public class SecurityPermissionService
		extends ServiceDAO<String, SecurityPermission, SecurityPermissionDAL> {


}
