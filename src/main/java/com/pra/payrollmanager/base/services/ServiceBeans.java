package com.pra.payrollmanager.base.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.security.authorization.AuthorityService;

import lombok.Setter;

abstract public class ServiceBeans<DAL extends BaseDAL<?, ?>> {

	@Autowired
	@Setter
	protected DAL dataAccessLayer;

	public DAL dataAccessLayer() {
		return dataAccessLayer;
	}

	@Autowired
	@Setter
	protected ModelMapper modelMapper;

	public ModelMapper modelMapper() {
		return modelMapper;
	}
	
	public AuthorityService authorityService() {
		return dataAccessLayer.authorityService();
	}

}
