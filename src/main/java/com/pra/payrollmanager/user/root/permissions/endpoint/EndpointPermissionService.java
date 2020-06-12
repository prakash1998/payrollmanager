package com.pra.payrollmanager.user.root.permissions.endpoint;

import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDAO;

@Service
public class EndpointPermissionService
		extends ServiceDAO<String, EndpointPermission, EndpointPermissionDAL> {


}
