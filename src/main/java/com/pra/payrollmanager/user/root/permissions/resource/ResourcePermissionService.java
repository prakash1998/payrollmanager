package com.pra.payrollmanager.user.root.permissions.resource;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDAO;

@Service
public class ResourcePermissionService
		extends ServiceDAO<ObjectId, ResourceDAO, ResourcePermissionDAL> {


}
