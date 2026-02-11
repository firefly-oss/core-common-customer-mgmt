-- ======================================================
-- FLYWAY MIGRATION V3: CREATE CASTS
-- ======================================================
-- This migration creates implicit varchar casts for all enum types
-- This allows automatic conversion from varchar to enum types in PostgreSQL

-- Cast for party_kind_enum
CREATE CAST (varchar AS party_kind_enum) WITH INOUT AS IMPLICIT;

-- Cast for gender_enum
CREATE CAST (varchar AS gender_enum) WITH INOUT AS IMPLICIT;

-- Cast for marital_status_enum
CREATE CAST (varchar AS marital_status_enum) WITH INOUT AS IMPLICIT;

-- Cast for residency_status_enum
CREATE CAST (varchar AS residency_status_enum) WITH INOUT AS IMPLICIT;

-- Cast for status_code_enum
CREATE CAST (varchar AS status_code_enum) WITH INOUT AS IMPLICIT;

-- Cast for address_kind_enum
CREATE CAST (varchar AS address_kind_enum) WITH INOUT AS IMPLICIT;

-- Cast for email_kind_enum
CREATE CAST (varchar AS email_kind_enum) WITH INOUT AS IMPLICIT;

-- Cast for phone_kind_enum
CREATE CAST (varchar AS phone_kind_enum) WITH INOUT AS IMPLICIT;


-- Cast for provider_status_enum
CREATE CAST (varchar AS provider_status_enum) WITH INOUT AS IMPLICIT;