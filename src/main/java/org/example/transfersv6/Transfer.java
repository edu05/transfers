package org.example.transfersv6;

import java.util.UUID;

public class Transfer {

    public final UUID from;
    public final UUID to;
    public final int amount;

    public Transfer(UUID from, UUID to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}
