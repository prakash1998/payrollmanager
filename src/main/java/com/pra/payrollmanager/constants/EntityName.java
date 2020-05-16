package com.pra.payrollmanager.constants;

public enum EntityName {

	SECURITY_COMPANY("SECURITY_COMPANY"), COMPANY("COMPANY_DETAILS"), PERMISSION(
			"SECURITY_PERMISSIONS"), FEATURE_PERMISSION(
					"FEATURE_PERMISSIONS"), ENDPOINT_PERMISSION(
							"ENDPOINT_PERMISSIONS"), SCREEN_PERMISSION("SCREEN_PERMISSIONS"),

	SECURITY_USER("SECURITY_USERS"), USER("USERS"), ROLE("ROLES"), BASE_TEST(
			"BASE_TEST_TEST"), USER_ROLE_MAP(
					"USER_ROLE_MAPPING"), ROLE_PERMISSION_MAP(
							"ROLE_PERMISSION_MAPPING"), ROLE_ENDPOINT_MAP("ROLE_ENDPOINT_MAPPING"),
	PRODUCT("PRODUCTS");

	String tableName;

	private EntityName(String tableName) {
		this.tableName = tableName;
	}

	public String table() {
		return this.tableName;
	}
}
