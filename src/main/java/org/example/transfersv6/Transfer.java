package org.example.transfersv6;

import java.util.UUID;

public class Transfer {

    public UUID from;
    public UUID to;
    public int amount;

    public Transfer(UUID from, UUID to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}
