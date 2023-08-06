package org.example.transfersv4;

import org.example.transfersv4.messages.account.TransferLegAbortRequested;
import org.example.transfersv4.messages.account.TransferLegCommitRequested;
import org.example.transfersv4.messages.transferprocessor.TransferLegAborted;
import org.example.transfersv4.messages.transferprocessor.TransferLegCommitted;
import org.example.transfersv4.messages.account.TransferLegApprovalRequested;
import org.example.transfersv4.messages.transferprocessor.TransferLegApproved;
import org.example.transfersv4.messages.transferprocessor.TransferLegRejected;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Account extends Actor {

    private final UUID id = UUID.randomUUID();
    private int amount = 1000;
    private Map<UUID, Integer> preparedTransfers = new HashMap<>();
    private boolean withdrawalInFlight = false;
    public TransferProcessor transferProcessor;

    @Override
    protected void process(Object message) {
        if (message instanceof TransferLegApprovalRequested) {
            process((TransferLegApprovalRequested) message);
        } else if (message instanceof TransferLegCommitRequested) {
            process((TransferLegCommitRequested) message);
        } else if (message instanceof TransferLegAbortRequested) {
            process((TransferLegAbortRequested) message);
        } else if (message instanceof ReportRequest) {
            process((ReportRequest) message);
        } else {
            throw new RuntimeException("wrong message " + message);
        }
    }

    private void process(ReportRequest message) {
        System.out.println("acc " + id + " has " + amount + " at " + message.epoch());
    }

    private void process(TransferLegApprovalRequested transferLegApprovalRequested) {
        int transferAmount = transferLegApprovalRequested.amount();
        UUID transferId = transferLegApprovalRequested.id();
        if (transferAmount > 0) {
            preparedTransfers.put(transferId, transferAmount);
            transferProcessor.queue(new TransferLegApproved(transferId, id));
        } else if (withdrawalInFlight) {
            throw new RuntimeException("look into this, it might be why some accounts go to 0 " +
                    "maybe the order of the ifs " +
                    "in any case u need an explanation for that behaviour");
//            this.queue(transferLegApprovalRequested);
        } else if (amount + transferAmount < 0) {
            transferProcessor.queue(new TransferLegRejected(transferId));
        } else {
            preparedTransfers.put(transferId, transferAmount);
            withdrawalInFlight = true;
            transferProcessor.queue(new TransferLegApproved(transferId, id));
        }
    }

    private void process(TransferLegCommitRequested transferLegCommitRequested) {
        Integer transferAmount = preparedTransfers.remove(transferLegCommitRequested.id());
        if (transferAmount < 0) {
            withdrawalInFlight = false;
        }
        int oldAmount = amount;
        amount += transferAmount;
        if (amount < 0) {
            throw new RuntimeException("tx id " + transferLegCommitRequested.id() + " for " + transferAmount + " when amount was " + oldAmount);
        }
        transferProcessor.queue(new TransferLegCommitted(transferLegCommitRequested.id(), id));
    }

    private void process(TransferLegAbortRequested abort) {
        Integer transferAmount = preparedTransfers.remove(abort.id());
        if (transferAmount != null && transferAmount < 0) {
            withdrawalInFlight = false;
        }

        transferProcessor.queue(new TransferLegAborted(abort.id()));
    }

    public UUID getId() {
        return id;
    }
}
