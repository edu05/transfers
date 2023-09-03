package org.example.transfersv6;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

import static org.example.transfersv6.Utils.executeOnNormalThread;

public class Main {
    static { System.setProperty("logback.configurationFile", "logback.xml");}

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        IntStream.range(0, 640).forEach(Account::new);

        IntStream.range(0, 10).forEach(i -> executeOnNormalThread("TL" + i , new TransferListener()));

        LOGGER.info("lol goodbye");
        while (true) {
            Thread.sleep(10000);
        }
    }
}
