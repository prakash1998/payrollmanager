package com.pra.payrollmanager.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.security.authorization.AuthorityService;

import lombok.Setter;

abstract public class BaseControl<SERVICE extends BaseServiceAuditDTO<?, ?, ?, ?>> {

	@Autowired
	protected AuthorityService authService;
	
	@Autowired
	@Setter
	protected SERVICE service;
}
