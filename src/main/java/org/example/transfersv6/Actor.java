package org.example.transfersv6;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.transfersv6.Utils.createInbox;
import static org.example.transfersv6.Utils.executeOnThread;
import static org.example.transfersv6.Utils.freeThread;

public abstract class Actor<T> implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);
    private static final int FINISHED = 1_000;
    private static final Map<String, Inbox> INBOXES = new ConcurrentHashMap<>();

    public final String id;
    private final long startTime;
    private final Inbox<T> inbox = createInbox();

    public Actor(String id) {
        this.id = id;
        this.INBOXES.put(id, inbox);
        this.startTime = System.currentTimeMillis();
        executeOnThread(this);
    }

    public static void send(String to, Transfer message) {
        INBOXES.get(to).add(message);
    }

    @Override
    public final void run() {
        int passesWithoutUpdate = 0;
        while (true) {
            T nextMessage = inbox.poll();
            if (nextMessage != null) {
                process(nextMessage);
                passesWithoutUpdate = 0;
                if (Math.random() > 0.999) {
//                    LOGGER.info(state() + " qq " + Duration.of(Instant.now().toEpochMilli() - startTime, ChronoUnit.MILLIS));
                }
            } else {
                if (passesWithoutUpdate == FINISHED) {
                    freeThread(500);
                    continue;
                }
                passesWithoutUpdate++;

                if (passesWithoutUpdate == FINISHED) {
                    LOGGER.info("aaa " + state() + " qq " + Duration.of(Instant.now().toEpochMilli() - startTime, ChronoUnit.MILLIS));
                }
                freeThread();
            }
        }
    }

    abstract void process(T message);

    abstract String state();
}
