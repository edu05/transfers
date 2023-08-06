package org.example.transfersv6;

public class TransferRejectionProcessor extends Actor<Transfer> {

    private int rejectedTransfers = 0;

    @Override
    protected void process(Transfer message) {
        rejectedTransfers++;
        if (rejectedTransfers % 500 == 0) {
            throw new RuntimeException();
        }
    }

    @Override
    String state() {
        return "rejected " + rejectedTransfers;
    }
}
