package com.pra.payrollmanager.entity;

public enum CommonEntityNames implements EntityName {

	SECURITY_COMPANY(
			"SECURITY_COMPANY", true
	), COMPANY(
			"COMPANY_DETAILS", true
	), PERMISSION(
			"SECURITY_PERMISSIONS"
	), FEATURE_PERMISSION(
			"FEATURE_PERMISSIONS"
	), ENDPOINT_PERMISSION(
			"ENDPOINT_PERMISSIONS"
	), SCREEN_PERMISSION(
			"SCREEN_PERMISSIONS"
	), HOTEL_CATEGORY(
			"HOTEL_CATEGORY"
	), COMMON_FILES(
			"COMMON_FILES", true
	);

	String tableName;

	boolean withAudit = false;

	private CommonEntityNames(String tableName) {
		this.tableName = tableName;
	}

	private CommonEntityNames(String tableName, boolean withAudit) {
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
