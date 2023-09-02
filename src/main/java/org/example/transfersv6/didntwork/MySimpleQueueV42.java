package org.example.transfersv6.didntwork;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MySimpleQueueV42<T> {

    private final LinkedBlockingDeque<T>[] transfers = new LinkedBlockingDeque[30];
    private int index = 0;

    public MySimpleQueueV42() {
        for (int i = 0; i < transfers.length; i++) {
            transfers[i] = new LinkedBlockingDeque<>(1_000);
        }
    }


    public void add(UUID senderId, T transfer) {
        try {
            transfers[index].put(transfer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        index = (index + 1) % transfers.length;
    }

    public T poll() {
        for (int i = 0; i < transfers.length; i++) {
            T nextTransfer = null;
            try {
                nextTransfer = transfers[i].take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (nextTransfer != null) {
                return nextTransfer;
            }
        }
        return null;
    }
}
