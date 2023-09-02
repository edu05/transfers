package org.example.transfersv6.didntwork;

import org.example.transfersv6.Transfer;

import java.util.Queue;
import java.util.UUID;

public record SenderAndQueue<T>(UUID sender, Queue<T> queue) {
}
