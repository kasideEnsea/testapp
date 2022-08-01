ALTER TABLE testApp_user ADD COLUMN validation_code VARCHAR(10);
ALTER TABLE testApp_user ADD COLUMN is_valid boolean DEFAULT false;