
--ok
CREATE TABLE professions(
    profession_id UUID NOT NULL PRIMARY KEY,
    min_salary INTEGER NOT NULL,
    max_salary INTEGER NOT NULL,
    title VARCHAR(50) NOT NULL
);

--ok
CREATE TABLE app_users(
    user_id UUID NOT NULL PRIMARY KEY,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

--ok
CREATE TABLE employees(
    employee_id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    salary INTEGER NOT NULL,
    profession_id UUID NOT NULL REFERENCES professions(profession_id),
    user_id UUID NOT NULL REFERENCES app_users(user_id)
);

--ok
CREATE TABLE vehicles(
    vehicle_id UUID NOT NULL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    power INTEGER NOT NULL
);

--ok
CREATE TABLE refuelling(
    refuel_id UUID NOT NULL PRIMARY KEY,
    vehicle_id UUID NOT NULL REFERENCES vehicles(vehicle_id),
    amount INTEGER NOT NULL,
    price_per_litre FLOAT NOT NULL,
    country VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    refuel_date DATE NOT NULL
);

--ok
CREATE TABLE cargo(
    cargo_id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL
);

--ok
CREATE TABLE companies(
    company_id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL
);

--ok
CREATE TABLE complaints(
    complaint_id UUID NOT NULL PRIMARY KEY,
    employee_id UUID NOT NULL REFERENCES employees(employee_id),
    company_id UUID NOT NULL REFERENCES companies(company_id),
    description VARCHAR(150) NOT NULL
);

--ok
CREATE TABLE deliveries(
    delivery_id UUID NOT NULL PRIMARY KEY,
    employee_id UUID NOT NULL REFERENCES employees(employee_id),
    from_company_id UUID NOT NULL REFERENCES companies(company_id),
    to_company_id UUID NOT NULL REFERENCES companies(company_id),
    vehicle_id UUID NOT NULL REFERENCES vehicles(vehicle_id),
    weight INTEGER NOT NULL,
    cargo_id UUID NOT NULL REFERENCES cargo(cargo_id),
    start_date DATE NOT NULL,
    finish_date DATE,
    delay_days INTEGER
);

--ok
CREATE TABLE accidents(
    accident_id UUID NOT NULL PRIMARY KEY,
    delivery_id UUID NOT NULL REFERENCES deliveries(delivery_id),
    event_date DATE NOT NULL,
    description VARCHAR(150) NOT NULL
);

