package com.pra.payrollmanager.admin.accounting.customer;

import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.AuditServiceDAO;

@Service
public class CustomerService
		extends AuditServiceDAO<String, CustomerDAO, CustomerDAL> {

}
