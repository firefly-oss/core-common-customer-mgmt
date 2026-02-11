-- ======================================================
-- V5: Modify legal_entity headcount column to BIGINT
-- ======================================================
-- Change the headcount column from UUID to BIGINT in the legal_entity table
-- This allows storing numerical employee count values instead of UUID references

ALTER TABLE legal_entity 
ALTER COLUMN headcount TYPE BIGINT USING NULL;