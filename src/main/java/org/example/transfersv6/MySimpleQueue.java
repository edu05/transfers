package org.example.transfersv6;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MySimpleQueue<T> {

    private final Map<UUID, Queue<T>> messages = new ConcurrentHashMap<>();

    public void add(UUID senderId, T message) {
        Queue<T> queue = messages.computeIfAbsent(senderId, k -> new ConcurrentLinkedQueue<>());
        queue.add(message);
    }

    public T poll() {
        for (Queue<T> queue : messages.values()) {
            T nextTransfer = queue.poll();
            if (nextTransfer != null) {
                return nextTransfer;
            }
        }
        return null;
    }
}
