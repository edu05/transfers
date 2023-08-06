package org.example.transfersv5;

import org.example.transfersv5.messages.transferprocessor.TransferRequested;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TransferClient transferClient = new TransferClient();
        Account firstAccount = new Account();
        Account secondAccount = new Account();
        Account thirdAccount = new Account();
        Account fourthAccount = new Account();
        Account fifthAccount = new Account();
        List<Account> accounts = new ArrayList<>();
        accounts.add(firstAccount);
        accounts.add(secondAccount);
        accounts.add(thirdAccount);
        accounts.add(fourthAccount);
        accounts.add(fifthAccount);
        TransferProcessor transferProcessor = new TransferProcessor(accounts, transferClient);
        accounts.forEach(a -> a.transferProcessor = transferProcessor);
        List<Actor> actors = List.of(firstAccount, secondAccount, thirdAccount, fourthAccount, fifthAccount, transferProcessor, transferClient);

        Random ran = new Random();
        new Thread(() -> {
            while (true) {
                IntStream.range(0, 100000).forEach(i -> {
                    int amount = ran.nextInt(1, 600);
                    int nextFrom = ran.nextInt(0, 5);
                    int nextTo = ran.nextInt(0, 5);
                    if (nextFrom != nextTo) {

                        transferProcessor.queue(new TransferRequested(UUID.randomUUID(), amount, accounts.get(nextFrom).getId(), accounts.get(nextTo).getId()));
                    }
                });
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        for (Actor actor : actors) {
            new Thread(actor).start();
        }


        new Thread(() -> {
            int epoch = 0;
            while (true) {
                for (Account account : accounts) {
                    account.queue(new ReportRequest(epoch));
                }
                transferProcessor.queue(new ReportRequest(epoch));
                epoch++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        Thread.sleep(10000);

        transferProcessor.queue(new TransferRequested(UUID.randomUUID(), 100, accounts.get(0).getId(), accounts.get(1).getId()));
        transferProcessor.queue(new TransferRequested(UUID.randomUUID(), 200, accounts.get(1).getId(), accounts.get(0).getId()));
    }
}
