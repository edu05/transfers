package org.example.transfersv6;

public class TransferRejectionProcessor extends Actor<Transfer> {

    private int rejectedTransfers = 0;

    @Override
    protected void process(Transfer message) {
        rejectedTransfers++;
    }

    @Override
    String state() {
        return "rejected " + rejectedTransfers;
    }
}
