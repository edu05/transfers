package org.example.transfersv2;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static org.example.transfersv2.PrepareTransferResult.READY;

public class Transferer {

    private final Map<UUID, Account> accounts;

    public Transferer(List<Account> accounts) {
        this.accounts = accounts.stream().collect(Collectors.toMap(Account::getId, identity()));
    }

    public void transfer(UUID from, UUID to, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        Account fromAccount = accounts.get(from);
        Account toAccount = accounts.get(to);
        PrepareTransferResult fromTransferResult = fromAccount.prepareTransfer(amount);
        PrepareTransferResult toTransferResult = toAccount.prepareTransfer(-amount);
        if (toTransferResult == READY && fromTransferResult == READY) {
            fromAccount.commitPreparedTransfer();
            toAccount.commitPreparedTransfer();
        } else if (toTransferResult == PrepareTransferResult.NOT_ENOUGH_MONEY) {
            fromAccount.revertPreparedTransfer();
            toAccount.revertPreparedTransfer();
        } else {
//            while()
        }

    }
}
