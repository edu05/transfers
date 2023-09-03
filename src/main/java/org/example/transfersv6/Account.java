package org.example.transfersv6;

import static org.example.transfersv6.TransferRejectionProcessor.TRANSFER_REJECTION_PROCESSOR;

public class Account extends Actor<Transfer> {

    private int amount = 100_000_000;
    private int numOfNegativeTxs = 0;
    private int numOfPositiveTxs = 0;

    public Account(int num) {
        super("A" + num);
    }

    public void process(Transfer transfer) {
        if (id.equals(transfer.to())) {
            amount += transfer.amount();
            numOfPositiveTxs++;
            return;
        }
        if (amount - transfer.amount() < 0) {
            send(TRANSFER_REJECTION_PROCESSOR.id, transfer);
            return;
        }
        amount -= transfer.amount();
        numOfNegativeTxs++;
        send(transfer.to(), transfer);
    }

    @Override
    String state() {
        return id + " has " + amount + " after " + numOfNegativeTxs + " and " + numOfPositiveTxs;
    }
}