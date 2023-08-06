package org.example.transfersv6;

import java.util.Queue;
import java.util.UUID;

public record SenderAndQueue(UUID sender, Queue<Transfer> queue) {
}
