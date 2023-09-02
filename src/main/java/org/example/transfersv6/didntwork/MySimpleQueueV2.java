package org.example.transfersv6.didntwork;

import org.example.transfersv6.Transfer;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MySimpleQueueV2<T> {

    private final SenderAndQueue<T>[] transfers = new SenderAndQueue[100];
    private int index = 0;

    public void add(UUID senderId, T transfer) {
        for (int i = 0; i < index; i++) {
            if (transfers[i] != null) {
                transfers[i].queue().add(transfer);
                return;
            }
        }
        SenderAndQueue<T> senderAndQueue = new SenderAndQueue<>(senderId, new ConcurrentLinkedQueue<>());
        transfers[index] = senderAndQueue;
        senderAndQueue.queue().add(transfer);
        index++;
    }

    public T poll() {
        for (int i = 0; i < index; i++) {
            T nextTransfer = transfers[i].queue().poll();
            if (nextTransfer != null) {
                return nextTransfer;
            }
        }
        return null;
    }
}
