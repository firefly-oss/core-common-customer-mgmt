-- Generic industry classification, party attributes, and an ownership hierarchy.
-- Applied scripts are left unchanged. Existing CNAE values and lending facts are copied
-- forward, then the narrow columns are removed.

ALTER TABLE natural_person RENAME COLUMN family_name1 TO family_name;
ALTER TABLE natural_person RENAME COLUMN family_name2 TO second_family_name;

ALTER TABLE natural_person DROP CONSTRAINT IF EXISTS chk_natural_person_name;
ALTER TABLE natural_person ADD CONSTRAINT chk_natural_person_name
    CHECK (
        (given_name IS NOT NULL AND btrim(given_name) <> '')
        OR (family_name IS NOT NULL AND btrim(family_name) <> '')
    );

ALTER INDEX IF EXISTS idx_natural_person_family_name1 RENAME TO idx_natural_person_family_name;

CREATE TYPE industry_classification_scheme_enum AS ENUM (
    'ISIC',
    'NACE',
    'NAICS',
    'SIC',
    'CNAE',
    'OTHER'
);

CREATE CAST (varchar AS industry_classification_scheme_enum) WITH INOUT AS IMPLICIT;

CREATE TABLE party_industry_classification (
    party_industry_classification_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    party_id UUID NOT NULL,
    classification_scheme industry_classification_scheme_enum NOT NULL,
    classification_code VARCHAR(32) NOT NULL,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    effective_from TIMESTAMP WITH TIME ZONE,
    effective_to TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_party_industry_classification_party
        FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE,
    CONSTRAINT chk_party_industry_classification_dates
        CHECK (effective_to IS NULL OR effective_from IS NULL OR effective_to >= effective_from)
);

CREATE UNIQUE INDEX uq_party_industry_classification_primary
    ON party_industry_classification (party_id) WHERE is_primary;
CREATE INDEX idx_party_industry_classification_party
    ON party_industry_classification (party_id);

COMMENT ON TABLE party_industry_classification IS
    'Industry or activity classification for any party. scheme selects the catalog (ISIC, NACE, NAICS, SIC, CNAE, OTHER); code is the value in that catalog. At most one primary row per party.';

INSERT INTO party_industry_classification (
    party_id, classification_scheme, classification_code, is_primary, effective_from
)
SELECT party_id, 'CNAE', btrim(cnae_code), TRUE, CURRENT_TIMESTAMP
FROM legal_entity
WHERE cnae_code IS NOT NULL AND btrim(cnae_code) <> '';

ALTER TABLE legal_entity DROP COLUMN cnae_code;

CREATE TYPE party_attribute_type_enum AS ENUM (
    'STRING',
    'NUMBER',
    'DATE',
    'BOOLEAN'
);

CREATE CAST (varchar AS party_attribute_type_enum) WITH INOUT AS IMPLICIT;

CREATE TABLE party_attribute (
    party_attribute_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    party_id UUID NOT NULL,
    attribute_key VARCHAR(100) NOT NULL,
    attribute_type party_attribute_type_enum NOT NULL,
    attribute_value TEXT,
    currency_code VARCHAR(3),
    effective_from TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    effective_to TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_party_attribute_party
        FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE,
    CONSTRAINT chk_party_attribute_currency
        CHECK (currency_code IS NULL OR currency_code ~ '^[A-Z]{3}$'),
    CONSTRAINT chk_party_attribute_dates
        CHECK (effective_to IS NULL OR effective_to >= effective_from)
);

CREATE INDEX idx_party_attribute_party_key ON party_attribute (party_id, attribute_key);

COMMENT ON TABLE party_attribute IS
    'Effective-dated facts for any party. Lending, insurance, and commercial profiles store extra facts here instead of columns on natural_person.';

INSERT INTO party_attribute (party_id, attribute_key, attribute_type, attribute_value, effective_from)
SELECT party_id, 'employment_status', 'STRING', employment_status, COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM natural_person WHERE employment_status IS NOT NULL AND btrim(employment_status) <> '';

INSERT INTO party_attribute (party_id, attribute_key, attribute_type, attribute_value, effective_from)
SELECT party_id, 'employment_type', 'STRING', employment_type, COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM natural_person WHERE employment_type IS NOT NULL AND btrim(employment_type) <> '';

INSERT INTO party_attribute (party_id, attribute_key, attribute_type, attribute_value, effective_from)
SELECT party_id, 'employer', 'STRING', employer, COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM natural_person WHERE employer IS NOT NULL AND btrim(employer) <> '';

INSERT INTO party_attribute (party_id, attribute_key, attribute_type, attribute_value, effective_from)
SELECT party_id, 'employment_start_date', 'DATE', employment_start_date::text, COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM natural_person WHERE employment_start_date IS NOT NULL;

INSERT INTO party_attribute (party_id, attribute_key, attribute_type, attribute_value, effective_from)
SELECT party_id, 'annual_paydays', 'NUMBER', annual_paydays::text, COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM natural_person WHERE annual_paydays IS NOT NULL;

INSERT INTO party_attribute (party_id, attribute_key, attribute_type, attribute_value, effective_from)
SELECT party_id, 'housing_type', 'STRING', housing_type, COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM natural_person WHERE housing_type IS NOT NULL AND btrim(housing_type) <> '';

INSERT INTO party_attribute (party_id, attribute_key, attribute_type, attribute_value, effective_from)
SELECT party_id, 'housing_cost', 'NUMBER', housing_cost::text, COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM natural_person WHERE housing_cost IS NOT NULL;

INSERT INTO party_attribute (party_id, attribute_key, attribute_type, attribute_value, effective_from)
SELECT party_id, 'housing_start_date', 'DATE', housing_start_date::text, COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM natural_person WHERE housing_start_date IS NOT NULL;

INSERT INTO party_attribute (party_id, attribute_key, attribute_type, attribute_value, effective_from)
SELECT party_id, 'existing_loans', 'NUMBER', existing_loans::text, COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM natural_person WHERE existing_loans IS NOT NULL;

INSERT INTO party_attribute (party_id, attribute_key, attribute_type, attribute_value, effective_from)
SELECT party_id, 'other_debts', 'NUMBER', other_debts::text, COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM natural_person WHERE other_debts IS NOT NULL;

ALTER TABLE natural_person
    DROP COLUMN employment_status,
    DROP COLUMN employment_type,
    DROP COLUMN employer,
    DROP COLUMN employment_start_date,
    DROP COLUMN annual_paydays,
    DROP COLUMN housing_type,
    DROP COLUMN housing_cost,
    DROP COLUMN housing_start_date,
    DROP COLUMN existing_loans,
    DROP COLUMN other_debts;

CREATE TYPE party_relationship_kind_enum AS ENUM (
    'OWNERSHIP',
    'CONTROL',
    'PARENT',
    'OFFICER'
);

CREATE CAST (varchar AS party_relationship_kind_enum) WITH INOUT AS IMPLICIT;

ALTER TABLE party_relationship RENAME COLUMN from_party_id TO source_party_id;
ALTER TABLE party_relationship RENAME COLUMN to_party_id TO target_party_id;
ALTER TABLE party_relationship RENAME COLUMN start_date TO effective_from;
ALTER TABLE party_relationship RENAME COLUMN end_date TO effective_to;

ALTER INDEX IF EXISTS idx_party_relationship_from_party RENAME TO idx_party_relationship_source_party;
ALTER INDEX IF EXISTS idx_party_relationship_to_party RENAME TO idx_party_relationship_target_party;

ALTER TABLE party_relationship
    ADD COLUMN relationship_kind party_relationship_kind_enum,
    ADD COLUMN ownership_percentage NUMERIC(5, 2),
    ADD COLUMN tenant_id UUID;

ALTER TABLE party_relationship ALTER COLUMN relationship_type_id DROP NOT NULL;

UPDATE party_relationship AS relationship
SET tenant_id = party.tenant_id
FROM party
WHERE party.party_id = relationship.source_party_id
  AND relationship.tenant_id IS NULL;

ALTER TABLE party_relationship ALTER COLUMN tenant_id SET NOT NULL;

ALTER TABLE party_relationship
    ADD CONSTRAINT chk_party_relationship_percentage
        CHECK (ownership_percentage IS NULL OR (ownership_percentage >= 0 AND ownership_percentage <= 100));

ALTER TABLE party_relationship
    ADD CONSTRAINT chk_party_relationship_dates
        CHECK (effective_to IS NULL OR effective_from IS NULL OR effective_to >= effective_from);

CREATE INDEX idx_party_relationship_kind ON party_relationship (relationship_kind);
CREATE INDEX idx_party_relationship_target_kind ON party_relationship (target_party_id, relationship_kind);
CREATE INDEX idx_party_relationship_tenant ON party_relationship (tenant_id);

COMMENT ON COLUMN party_relationship.relationship_kind IS
    'OWNERSHIP, CONTROL, PARENT, or OFFICER. Holding, subsidiary, and sister are derived from PARENT and from ownership between legal entities.';
COMMENT ON COLUMN party_relationship.source_party_id IS
    'Owner, controlling party, parent legal entity, or officer.';
COMMENT ON COLUMN party_relationship.target_party_id IS
    'Owned or controlled legal entity, child legal entity, or the entity an officer serves.';
COMMENT ON COLUMN party_relationship.ownership_percentage IS
    'Optional share from 0 to 100. Owners do not have to sum to 100.';
