package com.pra.payrollmanager.entity;

public enum CompanyEntityNames implements EntityName {

	SECURITY_USER("SECURITY_USERS"), USER("USERS"), ROLE("ROLES"), BASE_TEST(
			"BASE_TEST_TEST"), USER_ROLE_MAP(
					"USER_ROLE_MAPPING"), ROLE_PERMISSION_MAP(
							"ROLE_PERMISSION_MAPPING"), ROLE_ENDPOINT_MAP("ROLE_ENDPOINT_MAPPING"), PRODUCT(
									"PRODUCTS"), STOCKBOOK("STOCK_BOOK"), NOTIFICATION(
											"NOTIFICATIONS"), NOTIFICATION_ACK(
													"NOTIFICATION_ACK"),
	HOTEL_MENU("HOTEL_MENU"),HOTEL_TABLE("HOTEL_TABLE"),HOTEL_TABLE_ALLOC("HOTEL_TABLE_ALLOC"),
	HOTEL_ORDERS("HOTEL_ORDERS"),HOTEL_ORDER_DETAIL("HOTEL_ORDER_DETAIL");

	String tableName;

	private CompanyEntityNames(String tableName) {
		this.tableName = tableName;
	}

	public String table() {
		return this.tableName;
	}
}
