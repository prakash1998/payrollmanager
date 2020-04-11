package com.pra.payrollmanager.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.services.next.NewBaseService;
import com.pra.payrollmanager.security.authorization.AuthorityService;

import lombok.Setter;

abstract public class NewBaseControl<SERVICE extends NewBaseService<?, ?, ?, ?>> {

	@Autowired
	protected AuthorityService authService;

	@Autowired
	@Setter
	protected SERVICE service;
}
