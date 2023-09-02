package org.example.transfersv6;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        List<Account> accounts = IntStream.range(0, 640).mapToObj(i -> new Account()).collect(Collectors.toList());

        TransferSubmitter transferSubmitter = new TransferSubmitter(accounts);
        transferSubmitter.submitTransfers(80_000_000, 20);

        ActorRepository.TRANSFER_REJECTION_PROCESSOR.start();
        for (Account account : accounts) {
            account.start();
        }
        LOGGER.info("lol goodbye");
        while (true) {
            Thread.sleep(10000);
        }
    }
}
