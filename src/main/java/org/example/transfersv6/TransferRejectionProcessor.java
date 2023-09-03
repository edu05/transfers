package org.example.transfersv6;

public class TransferRejectionProcessor extends Actor<Transfer> {
    public static final TransferRejectionProcessor TRANSFER_REJECTION_PROCESSOR = new TransferRejectionProcessor();

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
