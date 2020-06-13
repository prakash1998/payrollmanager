package com.pra.payrollmanager.entity;

public enum CommonEntityNames implements EntityName{

	SECURITY_COMPANY("SECURITY_COMPANY"), COMPANY("COMPANY_DETAILS"), PERMISSION(
			"SECURITY_PERMISSIONS"), FEATURE_PERMISSION(
					"FEATURE_PERMISSIONS"), ENDPOINT_PERMISSION(
							"ENDPOINT_PERMISSIONS"), SCREEN_PERMISSION("SCREEN_PERMISSIONS"),
	HOTEL_CATEGORY("HOTEL_CATEGORY");

	String tableName;

	private CommonEntityNames(String tableName) {
		this.tableName = tableName;
	}

	public String table() {
		return this.tableName;
	}
}
