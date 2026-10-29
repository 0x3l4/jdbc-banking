--liquibase formatted sql

--changeset alexander.vereta:003-create-transfers
CREATE TABLE transfers (
  id UUID NOT NULL,
  initiated_by BIGINT NOT NULL,
  idempotency_key UUID NOT NULL,
  from_account_id BIGINT NOT NULL,
  to_account_id BIGINT NOT NULL,
  amount NUMERIC(19, 4) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT pk_transfers
  PRIMARY KEY (id),
  
  CONSTRAINT fk_transfers_initiator
  FOREIGN KEY (initiated_by)
  REFERENCES users (id)
  ON DELETE RESTRICT,
  
  CONSTRAINT fk_transfers_source_account_currency
  FOREIGN KEY (from_account_id, currency)
  REFERENCES accounts (id, currency)
  ON DELETE RESTRICT,
  
  CONSTRAINT fk_transfers_target_account_currency
  FOREIGN KEY (to_account_id, currency)
  REFERENCES accounts (id, currency)
  ON DELETE RESTRICT,
  
  CONSTRAINT uq_transfers_initiator_idempotency
  UNIQUE (initiated_by, idempotency_key),
  
  CONSTRAINT chk_transfers_distinct_accounts
  CHECK (from_account_id <> to_account_id),
  
  CONSTRAINT chk_transfers_amount_positive
  CHECK (amount > 0),
  
  CONSTRAINT chk_transfers_currency
  CHECK (currency ~ '^[A-Z]{3}$')
);

COMMENT ON TABLE transfers IS 'Успешно завершённые переводы';

--rollback DROP TABLE transfers;