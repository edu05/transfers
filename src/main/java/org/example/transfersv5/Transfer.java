package org.example.transfersv5;

import java.util.UUID;

import static org.example.transfersv5.TransferStatus.ABORTED;
import static org.example.transfersv5.TransferStatus.APPROVAL_DENIED;
import static org.example.transfersv5.TransferStatus.APPROVED;
import static org.example.transfersv5.TransferStatus.COMMITTED;
import static org.example.transfersv5.TransferStatus.PARTIALLY_ABORTED;
import static org.example.transfersv5.TransferStatus.PARTIALLY_APPROVED;
import static org.example.transfersv5.TransferStatus.PARTIALLY_COMMITTED;
import static org.example.transfersv5.TransferStatus.REQUESTED;

public record Transfer(UUID id, UUID from, UUID to, int amount, TransferStatus status) {

    public Transfer partiallyApprove() {
        if (status != REQUESTED) {
            throw new RuntimeException("wrong transfer");
        }
        return new Transfer(id, from, to, amount, PARTIALLY_APPROVED);
    }

    public Transfer approve() {
        if (status != PARTIALLY_APPROVED) {
            throw new RuntimeException("wrong transfer " + status);
        }
        return new Transfer(id, from, to, amount, APPROVED);
    }

    public Transfer requestAbort() {
        if (status != REQUESTED && status != PARTIALLY_APPROVED) {
            throw new RuntimeException("wrong transfer " + status);
        }
        return new Transfer(id, from, to, amount, APPROVAL_DENIED);
    }

    public Transfer partiallyAbort() {
        if (status != APPROVAL_DENIED) {
            throw new RuntimeException("wrong transfer " + status);
        }
        return new Transfer(id, from, to, amount, PARTIALLY_ABORTED);
    }

    public Transfer abort() {
        if (status != PARTIALLY_ABORTED) {
            throw new RuntimeException("wrong transfer " + status);
        }
        return new Transfer(id, from, to, amount, ABORTED);
    }

    public Transfer partiallyCommit() {
        if (status != APPROVED) {
            throw new RuntimeException("wrong transfer " + status);
        }
        return new Transfer(id, from, to, amount, PARTIALLY_COMMITTED);
    }

    public Transfer commit() {
        if (status != PARTIALLY_APPROVED) {
            throw new RuntimeException("wrong transfer " + status);
        }
        return new Transfer(id, from, to, amount, COMMITTED);
    }
}
