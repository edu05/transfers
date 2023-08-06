package org.example.transfersv2;

import java.util.UUID;

public class Transfer {

    private final UUID id = UUID.randomUUID();
    private final int transferAmount;

    private Transfer(int transferAmount) {
        this.transferAmount = transferAmount;
    }

    public static Transfer withdrawal(int transferAmount) {
        if (transferAmount <= 0) {
            throw new IllegalArgumentException();
        }
        return new Transfer(-transferAmount);
    }

    public static Transfer deposit(int transferAmount) {
        if (transferAmount <= 0) {
            throw new IllegalArgumentException();
        }
        return new Transfer(transferAmount);
    }
}
