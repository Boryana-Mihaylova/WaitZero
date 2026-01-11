-- 1) Tables

CREATE TABLE IF NOT EXISTS locations (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL,
    address VARCHAR(255),
    timezone VARCHAR(100),
    active BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS services (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    avg_service_minutes INT NOT NULL,
    location_id BIGINT NOT NULL,
    active BOOLEAN NOT NULL,
    CONSTRAINT fk_services_location
    FOREIGN KEY (location_id) REFERENCES locations(id)
    );

-- 2) Seed

INSERT INTO locations (id, name, code, address, timezone, active)
VALUES
    (1, 'City Hospital Sofia', 'H', 'Sofia, Center', 'Europe/Sofia', true),
    (2, 'MedLab Diagnostics', 'L', 'Sofia, Mladost', 'Europe/Sofia', true),
    (3, 'Mladost Polyclinic – GP Area', 'G', 'Sofia, Mladost', 'Europe/Sofia', true),
    (4, 'Municipal Service Center', 'M', 'Sofia, Center', 'Europe/Sofia', true),
    (5, 'Vehicle Technical Inspection', 'V', 'Sofia, Center', 'Europe/Sofia', true)
    ON CONFLICT (id) DO NOTHING;

INSERT INTO services (id, name, avg_service_minutes, location_id, active)
VALUES
    (1, 'Registration desk', 10, 1, true),
    (2, 'X-ray', 15, 1, true),

    (3, 'Blood tests', 15, 2, true),
    (4, 'Urine analysis', 10, 2, true),

    (5, 'Dr. Ivanova', 15, 3, true),
    (6, 'Dr. Petrov', 15, 3, true),
    (7, 'Dr. Georgiev', 15, 3, true),

    (8, 'ID card application', 25, 4, true),
    (9, 'Address registration', 20, 4, true),
    (10, 'Certificate request', 15, 4, true),

    (11, 'Annual inspection', 35, 5, true)
    ON CONFLICT (id) DO NOTHING;