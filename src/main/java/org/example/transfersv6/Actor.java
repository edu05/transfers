package org.example.transfersv6;

import java.util.UUID;

public abstract class Actor<T> implements Runnable {

    public final UUID id = UUID.randomUUID();
    private long startTime = 0;
    private final MySimpleQueueV6<T> messageQueue = new MySimpleQueueV6<>();

    public void queue(UUID senderId, T message) {
        messageQueue.add(senderId, message);
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
            } else {
                if (count == 100_000) {
                    System.out.println(state());
                    stopCountingUntilNewMessageHasArrived = true;
                    count = 100_001;
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

    public void start() {
        startTime = System.currentTimeMillis();
        new Thread(this).start();
    }
    public long startTime() {
        return startTime;
    }
}
