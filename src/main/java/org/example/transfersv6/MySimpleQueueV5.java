package org.example.transfersv6;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MySimpleQueueV5<T> {

    private final Map<UUID, Queue<T>> messages = new ConcurrentHashMap<>();

    public void add(UUID senderId, T transfer) {
        Queue<T> queue = messages.computeIfAbsent(senderId, k -> new ConcurrentLinkedQueue<>());
        queue.add(transfer);
    }

    public T poll() {
        for (Queue<T> queue : messages.values()) {
            T nextMessage = queue.poll();
            if (nextMessage != null) {
                return nextMessage;
            }
        }
        return null;
    }
}
