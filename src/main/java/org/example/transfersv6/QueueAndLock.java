package org.example.transfersv6;

import java.util.Queue;
import java.util.concurrent.locks.Lock;

public record QueueAndLock<T>(Queue<T> queue, Lock lock) {
}
