-- ======================================================
-- FLYWAY MIGRATION V1: CREATE ENUMS
-- ======================================================
-- This migration creates all enum types required for the Party/Customer data model
-- Based on the ER diagram specifications

-- Party Kind Enum (for Party.partyKind)
CREATE TYPE party_kind_enum AS ENUM (
    'INDIVIDUAL', 
    'ORGANIZATION'
);

-- Gender Enum (for NaturalPerson.gender)
CREATE TYPE gender_enum AS ENUM (
    'MALE', 
    'FEMALE', 
    'NON_BINARY', 
    'UNSPECIFIED'
);

-- Marital Status Enum (for NaturalPerson.maritalStatus)
CREATE TYPE marital_status_enum AS ENUM (
    'SINGLE', 
    'MARRIED', 
    'DIVORCED', 
    'WIDOWED'
);

-- Residency Status Enum (for NaturalPerson.residencyStatus)
CREATE TYPE residency_status_enum AS ENUM (
    'RESIDENT', 
    'NON_RESIDENT'
);

-- Status Code Enum (for PartyStatus.statusCode)
CREATE TYPE status_code_enum AS ENUM (
    'ACTIVE', 
    'INACTIVE', 
    'SUSPENDED', 
    'PENDING', 
    'CLOSED'
);

-- Address Kind Enum (for Address.addressKind)
CREATE TYPE address_kind_enum AS ENUM (
    'HOME', 
    'WORK', 
    'MAILING', 
    'REGISTERED'
);

-- Email Kind Enum (for EmailContact.emailKind)
CREATE TYPE email_kind_enum AS ENUM (
    'PERSONAL', 
    'BUSINESS', 
    'OTHER'
);

-- Phone Kind Enum (for PhoneContact.phoneKind)
CREATE TYPE phone_kind_enum AS ENUM (
    'MOBILE', 
    'HOME', 
    'WORK', 
    'OTHER'
);


-- Provider Status Enum (for PartyProvider.providerStatus)
CREATE TYPE provider_status_enum AS ENUM (
    'ACTIVE', 
    'INACTIVE', 
    'ERROR', 
    'SYNCING'
);