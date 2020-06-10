package com.pra.payrollmanager.base.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.security.authorization.AuthorityService;

abstract public class DALBeans {

	@Autowired
	private MongoTemplate mongoTemplate;

	public MongoTemplate mongoTemplate() {
		return mongoTemplate;
	}
	
	@Autowired
	protected AuthorityService authorityService;
	
	public AuthorityService authorityService() {
		return authorityService;
	}
	
}
