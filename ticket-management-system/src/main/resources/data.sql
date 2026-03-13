INSERT INTO tickets (title, description, status, priority, created_at, updated_at)
VALUES
('Database connection timeout', 'Support request raised after repeated connection failures in the reporting service.', 'OPEN', 'HIGH', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('User unable to access dashboard', 'Application returns a 403 error for a valid internal user account.', 'IN_PROGRESS', 'MEDIUM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Daily batch job completed with warnings', 'Batch integration job completed but logged warnings for missing optional fields.', 'RESOLVED', 'LOW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
