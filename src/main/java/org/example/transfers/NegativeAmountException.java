package org.example.transfers;

import java.math.BigDecimal;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException(BigDecimal amount) {
        super("Not allowed to construct negative moneys " + amount.toPlainString());
    }
}
