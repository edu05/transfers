package org.example.transfers;

import java.math.BigDecimal;

public class Money {
    private final String currency;
    private final BigDecimal amount;

    private Money(BigDecimal amount, String currency) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException(amount);
        }
        this.amount = amount;
        this.currency = currency;
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO, "EUR");
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new DifferentCurrencyExceptions(this, other);
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new DifferentCurrencyExceptions(this, other);
        }
        return new Money(this.amount.subtract(other.amount), this.currency);
    }
}
