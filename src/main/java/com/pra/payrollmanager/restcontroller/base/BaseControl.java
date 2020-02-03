package com.pra.payrollmanager.restcontroller.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.security.authorization.servcie.AuthorityService;
import com.pra.payrollmanager.service.base.BaseServiceDTO;

import lombok.Setter;

abstract public class BaseControl<SERVICE extends BaseServiceDTO<?, ?, ?, ?>> {

	@Autowired
	protected AuthorityService authService;
	
	@Autowired
	@Setter
	protected SERVICE service;
}
