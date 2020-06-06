package com.pra.payrollmanager.entity;

public enum CompanyEntityNames implements EntityName{

	SECURITY_USER("SECURITY_USERS"), USER("USERS"), ROLE("ROLES"), BASE_TEST(
			"BASE_TEST_TEST"), USER_ROLE_MAP(
					"USER_ROLE_MAPPING"), ROLE_PERMISSION_MAP(
							"ROLE_PERMISSION_MAPPING"), ROLE_ENDPOINT_MAP("ROLE_ENDPOINT_MAPPING"), PRODUCT(
									"PRODUCTS"), STOCKBOOK("STOCK_BOOK"), NOTIFICATION(
											"NOTIFICATIONS"), NOTIFICATION_ACK("NOTIFICATION_ACK");

	String tableName;

	private CompanyEntityNames(String tableName) {
		this.tableName = tableName;
	}

	public String table() {
		return this.tableName;
	}
}