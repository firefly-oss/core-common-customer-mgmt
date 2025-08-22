package com.catalis.core.customer.interfaces.enums;

/**
 * Enum representing provider status options for party providers.
 * Maps to the PostgreSQL provider_status_enum.
 */
public enum ProviderStatus {
    ACTIVE,
    INACTIVE,
    ERROR,
    SYNCING
}