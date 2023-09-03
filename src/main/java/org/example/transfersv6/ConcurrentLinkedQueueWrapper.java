package org.example.transfersv6;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWrapper<T> implements Inbox<T> {
    private final ConcurrentLinkedQueue<T> proxy = new ConcurrentLinkedQueue<>();

    @Override
    public void add(T t) {
        proxy.add(t);
    }

    @Override
    public T poll() {
        return proxy.poll();
    }
}
