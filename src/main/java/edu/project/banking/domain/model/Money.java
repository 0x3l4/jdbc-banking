package edu.project.banking.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public final class Money {

    private final BigDecimal amount;

    private final Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");

        if (amount.precision() > 19 || amount.scale() > 4) {
            throw new IllegalArgumentException("Amount exceeds NUMERIC(19,4)");
        }

        this.amount = amount;
        this.currency = currency;
    }

    public Money add(Money other) {
        requireSameCurrency(other);

        return new Money(
                amount.add(other.amount),
                currency
        );
    }

    public Money subtract(Money other) {
        requireSameCurrency(other);

        return new Money(
                amount.subtract(other.amount),
                currency
        );
    }

    public Money roundToCurrencyScale(Currency currency) {
        int scale = currency.getDefaultFractionDigits();

        if (scale < 0) {
            throw new IllegalStateException("Currency has no default fraction digits");
        }

        return new Money(amount.setScale(scale, RoundingMode.HALF_EVEN), currency);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass())
            return false;

        Money money = (Money) other;

        return Objects.equals(amount, money.amount) && Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other, "other");

        if (!currency.equals(other.currency))
            throw new IllegalArgumentException("Currency mismatch: " + currency + "  vs" + other.currency);
    }
}
