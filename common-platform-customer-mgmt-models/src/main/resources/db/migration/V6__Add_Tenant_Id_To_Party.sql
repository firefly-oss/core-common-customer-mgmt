-- ======================================================
-- FLYWAY MIGRATION V6: ADD TENANT_ID TO PARTY TABLE
-- ======================================================
-- This migration adds the tenant_id column to the party table
-- The tenant_id represents the UUID identifier from the Firefly core banking
-- deployment where the customer resides

-- Add tenant_id column to party table
ALTER TABLE party ADD COLUMN tenant_id UUID NOT NULL;

-- Create index on tenant_id for efficient querying by tenant
CREATE INDEX idx_party_tenant_id ON party(tenant_id);

-- Add comment to document the column purpose
COMMENT ON COLUMN party.tenant_id IS 'UUID identifier of the Firefly core banking deployment tenant where this customer resides';

