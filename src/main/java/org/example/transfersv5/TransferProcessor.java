package org.example.transfersv5;

import org.example.transfersv5.messages.account.TransferLegAbortRequested;
import org.example.transfersv5.messages.account.TransferLegApprovalRequested;
import org.example.transfersv5.messages.account.TransferLegCommitRequested;
import org.example.transfersv5.messages.transferclient.TransferAborted;
import org.example.transfersv5.messages.transferclient.TransferCommitted;
import org.example.transfersv5.messages.transferprocessor.TransferLegAborted;
import org.example.transfersv5.messages.transferprocessor.TransferLegApproved;
import org.example.transfersv5.messages.transferprocessor.TransferLegCommitted;
import org.example.transfersv5.messages.transferprocessor.TransferLegRejected;
import org.example.transfersv5.messages.transferprocessor.TransferRequested;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static org.example.transfersv5.TransferStatus.APPROVAL_DENIED;
import static org.example.transfersv5.TransferStatus.APPROVED;
import static org.example.transfersv5.TransferStatus.PARTIALLY_ABORTED;
import static org.example.transfersv5.TransferStatus.PARTIALLY_APPROVED;
import static org.example.transfersv5.TransferStatus.PARTIALLY_COMMITTED;
import static org.example.transfersv5.TransferStatus.REQUESTED;

public class TransferProcessor extends Actor {

    private final Map<UUID, Account> accountRegister;
    private final Map<UUID, Transfer> inFlightTransfers = new HashMap<>();
    private final TransferClient transferClient;

    public TransferProcessor(List<Account> accounts, TransferClient transferClient) {
        this.accountRegister = accounts.stream().collect(Collectors.toMap(Account::getId, identity()));
        this.transferClient = transferClient;
    }

    @Override
    protected void process(Object message) {
        if (message instanceof TransferRequested) {
            process((TransferRequested) message);
        } else if (message instanceof TransferLegApproved) {
            process((TransferLegApproved) message);
        } else if (message instanceof TransferLegRejected) {
            process((TransferLegRejected) message);
        } else if (message instanceof TransferLegAborted) {
            process((TransferLegAborted) message);
        } else if (message instanceof TransferLegCommitted) {
            process((TransferLegCommitted) message);
        }  else if (message instanceof ReportRequest) {
            process((ReportRequest) message);
        } else {
            throw new RuntimeException("message wrong " + message);
        }
    }

    private void process(ReportRequest message) {
        Map<TransferStatus, List<Transfer>> collect = inFlightTransfers.values().stream().collect(Collectors.groupingBy(Transfer::status));
        for (Map.Entry<TransferStatus, List<Transfer>> e : collect.entrySet()) {
            System.out.println("there are " + e.getValue().size() + " for status " + e.getKey() + " at epoch " + message.epoch());
        }
    }

    private void process(TransferRequested transferRequested) {
        Account fromAccount = accountRegister.get(transferRequested.from());
        Account toAccount = accountRegister.get(transferRequested.to());
        fromAccount.queue(new TransferLegApprovalRequested(transferRequested.id(), transferRequested.amount()));
        toAccount.queue(new TransferLegApprovalRequested(transferRequested.id(), -transferRequested.amount()));
        inFlightTransfers.put(transferRequested.id(), new Transfer(transferRequested.id(), transferRequested.from(), transferRequested.to(), transferRequested.amount(), REQUESTED));
    }

    private void process(TransferLegApproved transferLegApproved) {
        Transfer transfer = inFlightTransfers.get(transferLegApproved.id());
        if (transfer.status() == REQUESTED) {
            inFlightTransfers.put(transferLegApproved.id(), transfer.partiallyApprove());
        } else if (transfer.status() == PARTIALLY_APPROVED) {
            inFlightTransfers.put(transferLegApproved.id(), transfer.approve());
            Account fromAccount = accountRegister.get(transfer.from());
            Account toAccount = accountRegister.get(transfer.to());
            fromAccount.queue(new TransferLegCommitRequested(transferLegApproved.id()));
            toAccount.queue(new TransferLegCommitRequested(transferLegApproved.id()));
        } else if (transfer.status() == APPROVAL_DENIED) {
            //do nothing, wait for abort message
        } else if (transfer.status() == PARTIALLY_ABORTED) {
            //do nothing, wait for last abort message
        } else {
            //rest are unreachable
        }
    }

    private void process(TransferLegRejected transferLegRejected) {
        Transfer transfer = inFlightTransfers.get(transferLegRejected.id());
        Account fromAccount = accountRegister.get(transfer.from());
        Account toAccount = accountRegister.get(transfer.to());
        fromAccount.queue(new TransferLegAbortRequested(transferLegRejected.id()));
        toAccount.queue(new TransferLegAbortRequested(transferLegRejected.id()));
        inFlightTransfers.put(transferLegRejected.id(), transfer.requestAbort());
    }

    private void process(TransferLegAborted transferLegAborted) {
        Transfer transfer = inFlightTransfers.get(transferLegAborted.id());
        if (transfer.status() == APPROVAL_DENIED) {
            inFlightTransfers.put(transfer.id(), transfer.partiallyAbort());
        } else if (transfer.status() == PARTIALLY_ABORTED) {
            transferClient.process(new TransferAborted(transfer.id()));
            inFlightTransfers.remove(transfer.id());
//            System.out.println("aborting " + transfer);
            // inFlightTransfers.put(transfer.id(), transfer.abort()); //depending on what you might want to do
        }
    }

    private void process(TransferLegCommitted transferLegCommitted) {
        Transfer transfer = inFlightTransfers.get(transferLegCommitted.id());
        if (transfer.status() == APPROVED) {
            inFlightTransfers.put(transfer.id(), transfer.partiallyCommit());
        } else if (transfer.status() == PARTIALLY_COMMITTED) {
            transferClient.process(new TransferCommitted(transfer.id()));
            inFlightTransfers.remove(transfer.id());
            // inFlightTransfers.put(transfer.id(), transfer.commit()); //depending on what you might want to do
        }
    }
}
