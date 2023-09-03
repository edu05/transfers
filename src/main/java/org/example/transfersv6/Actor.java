package org.example.transfersv6;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.example.transfersv6.Utils.executeOnThread;
import static org.example.transfersv6.Utils.freeThread;

public abstract class Actor<T> implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);
    private static final int FINISHED = 50_000;
    private static final Map<UUID, ConcurrentLinkedQueue> INBOXES = new ConcurrentHashMap<>();

    public final UUID id = UUID.randomUUID();
    private long startTime = 0;
    private final ConcurrentLinkedQueue<T> messageQueue = new ConcurrentLinkedQueue<>();

    public Actor() {
        INBOXES.put(id, messageQueue);
        startTime = System.currentTimeMillis();
        executeOnThread(this);
    }

    public static void send(UUID to, Transfer message) {
        INBOXES.get(to).add(message);
    }

    @Override
    public final void run() {
        int count = 0;
        boolean stopCountingUntilNewMessageHasArrived = false;
        while (true) {
            T nextMessage = messageQueue.poll();
            if (nextMessage != null) {
                process(nextMessage);
                count = 0;
                stopCountingUntilNewMessageHasArrived = false;
                if (Math.random() > 0.999) {
//                    LOGGER.info(state() + " qq " + Duration.of(Instant.now().toEpochMilli() - startTime, ChronoUnit.MILLIS));
                }
            } else {
                freeThread();
                if (count == FINISHED) {
                    freeThread(500);
                    LOGGER.info("aaa " + state() + " qq " + Duration.of(Instant.now().toEpochMilli() - startTime, ChronoUnit.MILLIS));
                    stopCountingUntilNewMessageHasArrived = true;
                    count = FINISHED + 1;
                } else {
                    if (!stopCountingUntilNewMessageHasArrived) {
                        count++;
                    }
                }
            }
        }
    }

    abstract void process(T message);

    abstract String state();
}
