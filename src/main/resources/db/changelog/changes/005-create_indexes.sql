--liquibase formatted sql

--changeset alexander.vereta:005-create-indexes

-- Уникальность email без учёта регистра
CREATE UNIQUE INDEX uq_users_email_lower
ON users (lower(email));

-- Индекс по владельцу счёта
CREATE INDEX idx_accounts_owner
ON accounts (owner_id);

-- Индекс проводок по счёту и дате (сначала новые)
CREATE INDEX idx_ledger_account_date
ON ledger_entries (account_id, created_at DESC);

-- Индекс переводов по исходному счёту и дате
CREATE INDEX idx_transfers_source_date
ON transfers (from_account_id, created_at DESC);

-- Индекс переводов по целевому счёту и дате
CREATE INDEX idx_transfers_target_date
ON transfers (to_account_id, created_at DESC);

--rollback DROP INDEX idx_transfers_target_date;
--rollback DROP INDEX idx_transfers_source_date;
--rollback DROP INDEX idx_ledger_account_date;
--rollback DROP INDEX idx_accounts_owner;
--rollback DROP INDEX uq_users_email_lower;