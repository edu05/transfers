package org.example.transfersv6;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MySimpleQueueV6<T> {

    private final ConcurrentLinkedQueue<T> linkedQueue = new ConcurrentLinkedQueue<>();

    public void add(T message) {
        linkedQueue.add(message);
    }

    public T poll() {
        return linkedQueue.poll();
    }
}
