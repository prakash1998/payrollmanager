package com.pra.payrollmanager.security.authorization.mappings.roleresource;

public interface Resource<PK> {

	ResourceType resourceType();
	
	PK resourceId();
	
	String display();
	
}
