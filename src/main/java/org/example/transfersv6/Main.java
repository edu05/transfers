package org.example.transfersv6;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        List<UUID> accountIds = IntStream.range(0, 640).mapToObj(i -> new Account().id).collect(Collectors.toList());

        TransferSubmitter transferSubmitter = new TransferSubmitter(accountIds);
        transferSubmitter.submitTransfers(80_000_000, 20);

        LOGGER.info("lol goodbye");
        while (true) {
            Thread.sleep(10000);
        }
    }
}
