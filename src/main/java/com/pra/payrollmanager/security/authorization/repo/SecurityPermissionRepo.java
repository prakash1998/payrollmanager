package com.pra.payrollmanager.security.authorization.repo;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.repository.base.BaseRepo;
import com.pra.payrollmanager.security.authorization.dao.SecurityPermission;

@Repository
public interface SecurityPermissionRepo extends BaseRepo<SecurityPermission, String> {
	
}