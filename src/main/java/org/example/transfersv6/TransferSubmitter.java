package org.example.transfersv6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.example.transfersv6.ActorRepository.ACTOR_REPOSITORY;
import static org.example.transfersv6.Utils.executeOnThread;
import static org.example.transfersv6.Utils.freeThread;

public class TransferSubmitter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferSubmitter.class);

    private final List<Account> accounts;
    private final List<UUID> accountIds;

    public TransferSubmitter(List<Account> accounts) {
        this.accounts = accounts;
        this.accountIds = accounts.stream().map(a -> a.id).collect(Collectors.toList());
    }

    public void submitTransfers(int numTransfersTotal, int numberOfSenders) {
        accounts.forEach(Actor::start);

        int numTransfersPerSender = numTransfersTotal / numberOfSenders;
        int pauseInterval = numTransfersPerSender / 5;

        for (int i = 0; i < numberOfSenders; i++) {
            executeOnThread(() -> {
                ThreadLocalRandom current2 = ThreadLocalRandom.current();
                for (int j = 0; j < numTransfersPerSender; j++) {
                    if (j % pauseInterval == 0) {
                        freeThread();
                    }
                    UUID from = getRandomId(current2);
                    UUID to = getRandomId(from, current2);
                    ACTOR_REPOSITORY.getActor(from).queue(new Transfer(from, to, 3));
                }
                LOGGER.info("finished");
            });
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

    private UUID getRandomId(ThreadLocalRandom current) {
        int next = current.nextInt(0, accountIds.size());
        return accountIds.get(next);
    }
}
