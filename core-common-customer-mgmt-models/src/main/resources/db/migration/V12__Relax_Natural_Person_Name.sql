-- A person may have only a given name or only a family name.
-- The previous NOT NULL pair rejected mononyms and single-name legal identities.
-- Title is widened to the length the API already accepts.

ALTER TABLE natural_person ALTER COLUMN given_name DROP NOT NULL;
ALTER TABLE natural_person ALTER COLUMN family_name1 DROP NOT NULL;

ALTER TABLE natural_person DROP CONSTRAINT IF EXISTS chk_natural_person_name;
ALTER TABLE natural_person ADD CONSTRAINT chk_natural_person_name
    CHECK (
        (given_name IS NOT NULL AND btrim(given_name) <> '')
        OR (family_name1 IS NOT NULL AND btrim(family_name1) <> '')
    );

ALTER TABLE natural_person ALTER COLUMN title TYPE VARCHAR(50);
