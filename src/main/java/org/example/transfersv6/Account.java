package org.example.transfersv6;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.example.transfersv6.ActorRepository.ACTOR_REPOSITORY;
import static org.example.transfersv6.ActorRepository.TRANSFER_COMMITTED_PROCESSOR;
import static org.example.transfersv6.ActorRepository.TRANSFER_REJECTION_PROCESSOR;

public class Account extends Actor<Transfer> {

    private int amount = 100_000_000;
    private int numOfNegativeTxs = 0;
    private int numOfPositiveTxs = 0;
    private long lastCommit = -1;

    public Account() {
        super(true);
        ACTOR_REPOSITORY.putActor(id, this);
    }

    public void process(Transfer transfer) {
        if (id.equals(transfer.to())) {
            amount += transfer.amount();
//            System.out.println(id + " has " + amount);
//            System.out.print("-" + Duration.of(System.currentTimeMillis() - startTime(), ChronoUnit.MILLIS) + "-");
            TRANSFER_COMMITTED_PROCESSOR.queue(id, new TransferCommitted(transfer, System.currentTimeMillis()));
            numOfPositiveTxs++;
            long currentTimeMillis = System.currentTimeMillis();
            if (lastCommit < currentTimeMillis) {
                lastCommit = currentTimeMillis;
            }
            return;
        }
        if (amount - transfer.amount() < 0) {
            TRANSFER_REJECTION_PROCESSOR.queue(this.id, transfer);
            return;
        }
        amount -= transfer.amount();
        if (amount < 20) {
            System.out.println("amount less than 20");
        }
        numOfNegativeTxs++;
        ACTOR_REPOSITORY.getActor(transfer.to()).queue(this.id, transfer);
        long currentTimeMillis = System.currentTimeMillis();
        if (lastCommit < currentTimeMillis) {
            lastCommit = currentTimeMillis;
        }
    }

    @Override
    String state() {
        return id + " has " + amount + " after " + numOfNegativeTxs + " and " + numOfPositiveTxs + " with duration " + Duration.of(lastCommit - startTime(), ChronoUnit.MILLIS);
    }
}