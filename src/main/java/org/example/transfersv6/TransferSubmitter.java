package org.example.transfersv6;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class TransferSubmitter {

    private final List<Account> accounts;
    private final List<UUID> accountIds;

    public TransferSubmitter(List<Account> accounts) {
        this.accounts = accounts;
        this.accountIds = accounts.stream().map(a -> a.id).collect(Collectors.toList());
    }

    public void submitTransfers(int numTransfers) {
        List<Thread> threads = accounts.stream().map(account -> new Thread(() -> {
            UUID id = UUID.randomUUID();
            for (int i = 0; i < numTransfers; i++) {
                UUID anotherAccountId = getRandomId(account.id);
                account.queue(id, new Transfer(account.id, anotherAccountId, 3));
            }
        })).collect(Collectors.toList());

        for (Thread thread : threads) {
            thread.start();
        }
    }

    private UUID getRandomId(UUID id) {
        while (true) {
            int next = ThreadLocalRandom.current().nextInt(0, accountIds.size());
            UUID nextId = accountIds.get(next);
            if (!nextId.equals(id)) {
                return nextId;
            }
        }
    }
}
