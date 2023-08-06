package org.example.transfersv6;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Account> accounts = IntStream.range(0, 30).mapToObj(i -> new Account()).collect(Collectors.toList());

        TransferSubmitter transferSubmitter = new TransferSubmitter(accounts);
        transferSubmitter.submitTransfers(1_000_000);

        Thread.sleep(3000);
        ActorRepository.TRANSFER_REJECTION_PROCESSOR.start();
        for (Account account : accounts) {
            account.start();
        }
    }
}
