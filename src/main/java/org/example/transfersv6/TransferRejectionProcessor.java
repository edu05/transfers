package org.example.transfersv6;

public class TransferRejectionProcessor extends Actor<Transfer> {

    private int rejectedTransfers = 0;

    @Override
    protected void process(Transfer message) {
        rejectedTransfers++;
        TransferRepository.TRANSFER_REPOSITORY.recycle(message);
    }

    @Override
    String state() {
        return "rejected " + rejectedTransfers;
    }
}
