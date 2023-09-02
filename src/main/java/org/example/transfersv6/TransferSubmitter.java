package org.example.transfersv6;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.example.transfersv6.TransferRepository.TRANSFER_REPOSITORY;

public class TransferSubmitter {

    private final List<Account> accounts;
    private final List<UUID> accountIds;

    public TransferSubmitter(List<Account> accounts) {
        this.accounts = accounts;
        this.accountIds = accounts.stream().map(a -> a.id).collect(Collectors.toList());
    }

    public void submitTransfers(int numTransfers) {
        List<Runnable> runnables = accounts.stream().map(account -> (Runnable) () -> {
            UUID id = UUID.randomUUID();
            ThreadLocalRandom current = ThreadLocalRandom.current();
            for (int i = 0; i < numTransfers; i++) {
                UUID anotherAccountId = getRandomId(account.id, current);
                account.queue(id, new Transfer(account.id, anotherAccountId, 3));
            }
        }).collect(Collectors.toList());



        for (Runnable runnable : runnables) {
            new Thread(runnable).start();
        }
    }

    private UUID getRandomId(UUID id, ThreadLocalRandom current) {
        while (true) {
            int next = current.nextInt(0, accountIds.size());
            UUID nextId = accountIds.get(next);
            if (!nextId.equals(id)) {
                return nextId;
            }
        }
    }
}
