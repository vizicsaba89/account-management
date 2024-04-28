CREATE TABLE IF NOT EXISTS accounts (
     account_number DECIMAL PRIMARY KEY,
     account_holder_name VARCHAR NOT NULL,
     account_state VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
     id DECIMAL PRIMARY KEY,
     account_number BIGINT NOT NULL,
     type VARCHAR NOT NULL,
     amount DECIMAL NOT NULL,
     time_stamp TIMESTAMP NOT NULL
);
