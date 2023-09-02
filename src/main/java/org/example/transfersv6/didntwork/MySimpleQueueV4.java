package org.example.transfersv6.didntwork;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MySimpleQueueV4<T> {

    private final SenderAndQueue<T>[] transfers = new SenderAndQueue[30];

    public MySimpleQueueV4() {
        for (int i = 0; i < transfers.length; i++) {
//            transfers[i] = new SenderAndQueue<>
        }
    }


    public void add(UUID senderId, T transfer) {
//        for (int i = 0; i < index; i++) {
//            if (transfers[i] != null) {
//                transfers[i].queue().add(transfer);
//                return;
//            }
//        }
//        SenderAndQueue<T> senderAndQueue = new SenderAndQueue<>(senderId, new ConcurrentLinkedQueue<>());
//        transfers[index] = senderAndQueue;
//        senderAndQueue.queue().add(transfer);
//        index++;
    }

    public T poll() {
        for (int i = 0; i < transfers.length; i++) {
            T nextTransfer = transfers[i].queue().poll();
            if (nextTransfer != null) {
                return nextTransfer;
            }
        }
        return null;
    }
}
