package org.example.transfersv6;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class TransferCommittedProcessor extends Actor<TransferCommitted> {

    private int committedTransfers = 0;
    private long lastCommit = -1;

    public TransferCommittedProcessor() {
        super(true);
    }

    @Override
    protected void process(TransferCommitted message) {
        committedTransfers++;
        if (lastCommit < message.timestamp()) {
            lastCommit = message.timestamp();
        }
    }

    @Override
    String state() {
        return "committed " + committedTransfers + " with duration " + Duration.of(lastCommit - startTime(), ChronoUnit.MILLIS);
    }
}
