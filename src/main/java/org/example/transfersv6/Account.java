package org.example.transfersv6;

import static org.example.transfersv6.ActorRepository.ACTOR_REPOSITORY;
import static org.example.transfersv6.TransferRejectionProcessor.TRANSFER_REJECTION_PROCESSOR;

public class Account extends Actor<Transfer> {

    private int amount = 100_000_000;
    private int numOfNegativeTxs = 0;
    private int numOfPositiveTxs = 0;

    public void process(Transfer transfer) {
        if (id.equals(transfer.to)) {
            amount += transfer.amount;
            numOfPositiveTxs++;
            return;
        }
        if (amount - transfer.amount < 0) {
            TRANSFER_REJECTION_PROCESSOR.queue(transfer);
            return;
        }
        amount -= transfer.amount;
        numOfNegativeTxs++;
        Actor actor = ACTOR_REPOSITORY.getActor(transfer.to);
        actor.queue(transfer);
    }

    @Override
    String state() {
        return id + " has " + amount + " after " + numOfNegativeTxs + " and " + numOfPositiveTxs;
    }
}