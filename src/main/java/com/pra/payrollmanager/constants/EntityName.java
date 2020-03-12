package com.pra.payrollmanager.constants;

public enum EntityName {
	SECURITY_USER("SECURITY_USERS"), SECURITY_COMPANY("SECURITY_COMPANY"), USER("USERS"), COMPANY(
			"COMPANY_DETAILS"), ROLE("ROLES"), BASE_TEST(
					"BASE_TEST_TEST"), PERMISSION("SECURITY_PERMISSIONS"), USER_ROLE_MAP(
							"USER_ROLE_MAPPING"), USER_PERMISSION_MAP("USER_PERMISSION_MAPPING");

	String tableName;

	private EntityName(String tableName) {
		this.tableName = tableName;
	}

	public String table() {
		return this.tableName;
	}
}
