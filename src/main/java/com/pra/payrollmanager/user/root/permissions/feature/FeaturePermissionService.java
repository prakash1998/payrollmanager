package com.pra.payrollmanager.user.root.permissions.feature;

import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDAO;

@Service
public class FeaturePermissionService
		extends ServiceDAO<String, FeaturePermission, FeaturePermissionDAL> {


}
