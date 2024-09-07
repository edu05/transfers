package org.example.transfersv6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class DoYouUnderstandVirtualThreads {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoYouUnderstandVirtualThreads.class);



    public static void main(String[] args) {
        IntStream.range(0, 16).forEach(i ->
                Thread.ofVirtual().start(new MyJob())
//                new Thread(new MyJob()).start()
        );

        while (true) {

        }

    }

    public static class MyJob implements Runnable {

        @Override
        public void run() {
            LOGGER.info("hi " + Thread.currentThread().threadId());
            while (true) {
            }
        }
    }
}
