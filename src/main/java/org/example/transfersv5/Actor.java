package org.example.transfersv5;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Actor implements Runnable {

    private final Queue<Object> messageQueue = new ConcurrentLinkedQueue<>();

    public void queue(Object message) {
        messageQueue.add(message);
    }

    @Override
    public final void run() {
        while (true) {
            Object nextMessage = messageQueue.poll();
            if (nextMessage != null) {
                process(nextMessage);
            }
        }
    }

    protected abstract void process(Object message);
}
