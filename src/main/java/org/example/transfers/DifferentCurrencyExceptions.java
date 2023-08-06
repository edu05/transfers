package org.example.transfers;

public class DifferentCurrencyExceptions extends RuntimeException {
    public DifferentCurrencyExceptions(Money money, Money other) {
        super("appropriate message");
    }
}
