package com.pra.payrollmanager.entity;

public enum CompanyEntityNames implements EntityName {

	SECURITY_USER(
			"SECURITY_USERS", true
	), USER(
			"USERS", true
	), ROLE(
			"ROLES"
	), BASE_TEST(
			"BASE_TEST_TEST", true
	), USER_ROLE_MAP(
			"USER_ROLE_MAPPING"
	), ROLE_PERMISSION_MAP(
			"ROLE_PERMISSION_MAPPING"
	), ROLE_ENDPOINT_MAP(
			"ROLE_ENDPOINT_MAPPING"
	), ROLE_RESOURCE_MAP(
			"ROLE_RESOURCE_MAPPING"
	), PRODUCT(
			"PRODUCTS", true
	), STOCKBOOK(
			"STOCK_BOOK", true
	), NOTIFICATION(
			"NOTIFICATIONS", true
	), NOTIFICATION_ACK(
			"NOTIFICATION_ACK", true
	), SECURE_FILES(
			"SECURE_FILES", true
	), RESOURCES(
			"RESOURCES"
	), HOTEL_MENU(
			"HOTEL_MENU"
	), HOTEL_TABLE(
			"HOTEL_TABLE"
	), HOTEL_TABLE_ALLOC(
			"HOTEL_TABLE_ALLOC"
	), HOTEL_ORDERS(
			"HOTEL_ORDERS"
	), HOTEL_ORDER_DETAIL(
			"HOTEL_ORDER_DETAIL"
	);

	String tableName;

	boolean withAudit = false;

	private CompanyEntityNames(String tableName) {
		this.tableName = tableName;
	}

	private CompanyEntityNames(String tableName, boolean withAudit) {
		this.tableName = tableName;
		this.withAudit = withAudit;
	}

	public String table() {
		return this.tableName;
	}

	@Override
	public boolean withAudit() {
		return withAudit;
	}
}
