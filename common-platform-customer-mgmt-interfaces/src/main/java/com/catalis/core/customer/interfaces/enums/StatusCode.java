package com.catalis.core.customer.interfaces.enums;

/**
 * Enum representing status code options for party status.
 * Maps to the PostgreSQL status_code_enum.
 */
public enum StatusCode {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    PENDING,
    CLOSED
}