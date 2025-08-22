-- ======================================================
-- FLYWAY MIGRATION V2: CREATE TABLES
-- ======================================================
-- This migration creates all tables required for the Party/Customer data model
-- Based on the ER diagram specifications

-- ======================================================
-- PARTY (Supertype) - Canonical customer identifier
-- ======================================================
CREATE TABLE party (
    party_id BIGSERIAL PRIMARY KEY,
    party_kind party_kind_enum NOT NULL,
    preferred_language VARCHAR(5),
    source_system VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ======================================================
-- NATURAL PERSON (Subtype of PARTY)
-- ======================================================
CREATE TABLE natural_person (
    natural_person_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL UNIQUE,
    title VARCHAR(20),
    given_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    family_name1 VARCHAR(100) NOT NULL,
    family_name2 VARCHAR(100),
    date_of_birth DATE,
    birth_place VARCHAR(255),
    birth_country_id BIGINT,
    nationality_country_id BIGINT,
    gender gender_enum,
    marital_status marital_status_enum,
    tax_id_number VARCHAR(50),
    residency_status residency_status_enum,
    occupation VARCHAR(255),
    monthly_income DECIMAL(15,2),
    suffix VARCHAR(20),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- LEGAL ENTITY (Subtype of PARTY)
-- ======================================================
CREATE TABLE legal_entity (
    legal_entity_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL UNIQUE,
    legal_name VARCHAR(500) NOT NULL,
    trade_name VARCHAR(500),
    registration_number VARCHAR(100),
    tax_id_number VARCHAR(50),
    legal_form_id BIGINT,
    incorporation_date DATE,
    industry_description VARCHAR(500),
    headcount BIGINT,
    share_capital DECIMAL(15,2),
    website_url VARCHAR(500),
    incorporation_country_id BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- PARTY STATUS
-- ======================================================
CREATE TABLE party_status (
    party_status_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL,
    status_code status_code_enum NOT NULL,
    status_reason VARCHAR(500),
    valid_from TIMESTAMP WITH TIME ZONE NOT NULL,
    valid_to TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- IDENTITY DOCUMENT
-- ======================================================
CREATE TABLE identity_document (
    identity_document_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL,
    identity_document_category_id BIGINT NOT NULL,
    identity_document_type_id BIGINT NOT NULL,
    document_number VARCHAR(100) NOT NULL,
    issuing_country_id BIGINT NOT NULL,
    issue_date TIMESTAMP WITH TIME ZONE,
    expiry_date TIMESTAMP WITH TIME ZONE,
    issuing_authority VARCHAR(255),
    validated BOOLEAN NOT NULL DEFAULT FALSE,
    document_uri VARCHAR(1000),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- ADDRESS
-- ======================================================
CREATE TABLE address (
    address_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL,
    address_kind address_kind_enum NOT NULL,
    line1 VARCHAR(255) NOT NULL,
    line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    region VARCHAR(100),
    postal_code VARCHAR(20),
    country_id BIGINT NOT NULL,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- EMAIL CONTACT
-- ======================================================
CREATE TABLE email_contact (
    email_contact_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL,
    email VARCHAR(320) NOT NULL,
    email_kind email_kind_enum NOT NULL,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- PHONE CONTACT
-- ======================================================
CREATE TABLE phone_contact (
    phone_contact_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    phone_kind phone_kind_enum NOT NULL,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    extension VARCHAR(10),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- PARTY RELATIONSHIP
-- ======================================================
CREATE TABLE party_relationship (
    party_relationship_id BIGSERIAL PRIMARY KEY,
    from_party_id BIGINT NOT NULL,
    to_party_id BIGINT NOT NULL,
    relationship_type_id BIGINT NOT NULL,
    start_date TIMESTAMP WITH TIME ZONE NOT NULL,
    end_date TIMESTAMP WITH TIME ZONE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    notes VARCHAR(1000),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_party_id) REFERENCES party(party_id) ON DELETE CASCADE,
    FOREIGN KEY (to_party_id) REFERENCES party(party_id) ON DELETE CASCADE,
    CHECK (from_party_id != to_party_id)
);

-- ======================================================
-- PARTY ECONOMIC ACTIVITY
-- ======================================================
CREATE TABLE party_economic_activity (
    party_economic_activity_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL,
    economic_activity_id BIGINT NOT NULL,
    annual_turnover DECIMAL(15,2),
    currency_code VARCHAR(3),
    start_date TIMESTAMP WITH TIME ZONE NOT NULL,
    end_date TIMESTAMP WITH TIME ZONE,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- CONSENT
-- ======================================================
CREATE TABLE consent (
    consent_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL,
    consent_type_id BIGINT NOT NULL,
    granted BOOLEAN NOT NULL DEFAULT FALSE,
    granted_at TIMESTAMP WITH TIME ZONE,
    revoked_at TIMESTAMP WITH TIME ZONE,
    channel VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- POLITICALLY EXPOSED PERSON (PEP)
-- ======================================================
CREATE TABLE politically_exposed_person (
    pep_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL,
    pep BOOLEAN NOT NULL DEFAULT FALSE,
    category VARCHAR(100),
    public_position VARCHAR(255),
    country_of_position_id BIGINT,
    start_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE,
    notes VARCHAR(1000),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- PARTY PROVIDER (External system mapping)
-- ======================================================
CREATE TABLE party_provider (
    party_provider_id BIGSERIAL PRIMARY KEY,
    party_id BIGINT NOT NULL,
    provider_name VARCHAR(100) NOT NULL,
    external_reference VARCHAR(255) NOT NULL,
    provider_status provider_status_enum NOT NULL,
    last_sync_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- INDEXES FOR PERFORMANCE
-- ======================================================

-- Party indexes
CREATE INDEX idx_party_kind ON party(party_kind);
CREATE INDEX idx_party_source_system ON party(source_system);
CREATE INDEX idx_party_created_at ON party(created_at);

-- Natural Person indexes
CREATE INDEX idx_natural_person_party_id ON natural_person(party_id);
CREATE INDEX idx_natural_person_family_name1 ON natural_person(family_name1);
CREATE INDEX idx_natural_person_given_name ON natural_person(given_name);
CREATE INDEX idx_natural_person_tax_id ON natural_person(tax_id_number);

-- Legal Entity indexes
CREATE INDEX idx_legal_entity_party_id ON legal_entity(party_id);
CREATE INDEX idx_legal_entity_legal_name ON legal_entity(legal_name);
CREATE INDEX idx_legal_entity_tax_id ON legal_entity(tax_id_number);
CREATE INDEX idx_legal_entity_registration_number ON legal_entity(registration_number);

-- Party Status indexes
CREATE INDEX idx_party_status_party_id ON party_status(party_id);
CREATE INDEX idx_party_status_code ON party_status(status_code);
CREATE INDEX idx_party_status_valid_from ON party_status(valid_from);
CREATE INDEX idx_party_status_valid_to ON party_status(valid_to);

-- Identity Document indexes
CREATE INDEX idx_identity_document_party_id ON identity_document(party_id);
CREATE INDEX idx_identity_document_number ON identity_document(document_number);
CREATE INDEX idx_identity_document_type ON identity_document(identity_document_type_id);

-- Address indexes
CREATE INDEX idx_address_party_id ON address(party_id);
CREATE INDEX idx_address_kind ON address(address_kind);
CREATE INDEX idx_address_is_primary ON address(is_primary);
CREATE INDEX idx_address_country ON address(country_id);

-- Email Contact indexes
CREATE INDEX idx_email_contact_party_id ON email_contact(party_id);
CREATE INDEX idx_email_contact_email ON email_contact(email);
CREATE INDEX idx_email_contact_is_primary ON email_contact(is_primary);

-- Phone Contact indexes
CREATE INDEX idx_phone_contact_party_id ON phone_contact(party_id);
CREATE INDEX idx_phone_contact_number ON phone_contact(phone_number);
CREATE INDEX idx_phone_contact_is_primary ON phone_contact(is_primary);

-- Party Relationship indexes
CREATE INDEX idx_party_relationship_from_party ON party_relationship(from_party_id);
CREATE INDEX idx_party_relationship_to_party ON party_relationship(to_party_id);
CREATE INDEX idx_party_relationship_type ON party_relationship(relationship_type_id);
CREATE INDEX idx_party_relationship_active ON party_relationship(active);

-- Party Economic Activity indexes
CREATE INDEX idx_party_economic_activity_party_id ON party_economic_activity(party_id);
CREATE INDEX idx_party_economic_activity_economic_id ON party_economic_activity(economic_activity_id);
CREATE INDEX idx_party_economic_activity_is_primary ON party_economic_activity(is_primary);

-- Consent indexes
CREATE INDEX idx_consent_party_id ON consent(party_id);
CREATE INDEX idx_consent_type_id ON consent(consent_type_id);
CREATE INDEX idx_consent_granted ON consent(granted);

-- PEP indexes
CREATE INDEX idx_pep_party_id ON politically_exposed_person(party_id);
CREATE INDEX idx_pep_status ON politically_exposed_person(pep);

-- Party Provider indexes
CREATE INDEX idx_party_provider_party_id ON party_provider(party_id);
CREATE INDEX idx_party_provider_name ON party_provider(provider_name);
CREATE INDEX idx_party_provider_external_ref ON party_provider(external_reference);
CREATE INDEX idx_party_provider_status ON party_provider(provider_status);