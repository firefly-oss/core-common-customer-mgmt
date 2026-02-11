-- ======================================================
-- FLYWAY MIGRATION V4: CREATE PARTY GROUP MEMBERSHIP TABLE
-- ======================================================
-- This migration creates the party_group_membership table for managing
-- group memberships for parties

-- ======================================================
-- PARTY GROUP MEMBERSHIP
-- ======================================================
CREATE TABLE party_group_membership (
    party_group_membership_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    group_id UUID NOT NULL,
    party_id UUID NOT NULL,
    is_active BOOLEAN,
    start_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES party(party_id) ON DELETE CASCADE
);

-- ======================================================
-- INDEXES
-- ======================================================
CREATE INDEX idx_party_group_membership_party_id ON party_group_membership(party_id);
CREATE INDEX idx_party_group_membership_group_id ON party_group_membership(group_id);
CREATE INDEX idx_party_group_membership_is_active ON party_group_membership(is_active);