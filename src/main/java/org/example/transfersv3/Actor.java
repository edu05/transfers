package org.example.transfersv3;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface Actor {

    Queue<Message> messages = new ConcurrentLinkedQueue<>();

    default void queueMessage(Message message) {
        messages.add(message);
    }
    default void start() {
        while (true) {
            Message nextMessage = messages.poll();
            if (nextMessage != null) {
                processMessage(nextMessage);
            }
        }
    }

    void processMessage(Message message);

}
