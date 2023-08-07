package org.example.transfersv6;

import java.util.UUID;

public class Transfer {

    public UUID from;
    public UUID to;
    public int amount;

    public Transfer(UUID from, UUID uuid, int amount) {
        this.from = from;
        to = uuid;
        this.amount = amount;
    }
}
