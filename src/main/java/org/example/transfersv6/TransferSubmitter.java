package org.example.transfersv6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.example.transfersv6.Actor.send;
import static org.example.transfersv6.Utils.executeOnThread;
import static org.example.transfersv6.Utils.freeThread;

public class TransferSubmitter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferSubmitter.class);

    private final List<UUID> accountIds;

    public TransferSubmitter(List<UUID> accountIds) {
        this.accountIds = accountIds;
    }

    public void submitTransfers(int numTransfersTotal, int numberOfSenders) {
        int numTransfersPerSender = numTransfersTotal / numberOfSenders;
        int pauseInterval = numTransfersPerSender / 5;

        for (int i = 0; i < numberOfSenders; i++) {
            executeOnThread(() -> {
                ThreadLocalRandom current2 = ThreadLocalRandom.current();
                for (int j = 0; j < numTransfersPerSender; j++) {
                    if (j % pauseInterval == 0) {
                        freeThread(500);
                    }
                    UUID from = getRandomId(current2);
                    UUID to = getRandomId(from, current2);
                    send(from, new Transfer(from, to, 3));
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
