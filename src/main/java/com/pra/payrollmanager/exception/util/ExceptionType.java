package com.pra.payrollmanager.exception.util;

/**
 * Created by Arpit Khandelwal.
 */
public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found"),
    DUPLICATE_ENTITY("duplicate"),
    ENTITY_EXCEPTION("exception"),
    WRONG_CREDENTIAL("wrong.credential");

    String value;

    private ExceptionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
