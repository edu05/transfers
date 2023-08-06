package org.example.transfersv6;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MySimpleQueueV3 {

    private final SenderAndQueueV2[] transfers = new SenderAndQueueV2[100];
    private int index = 0;

    public void add(UUID senderId, Transfer transfer) {
        for (int i = 0; i < index; i++) {
            if (transfers[i] != null) {
                transfers[i].queue().add(transfer);
                return;
            }
        }
        SenderAndQueueV2 senderAndQueue = new SenderAndQueueV2(senderId, new MyQ());
        transfers[index] = senderAndQueue;
        senderAndQueue.queue().add(transfer);
        index++;
    }

    public Transfer poll() {
        for (int i = 0; i < index; i++) {
            Transfer nextTransfer = transfers[i].queue().poll();
            if (nextTransfer != null) {
                return nextTransfer;
            }
        }
        return null;
    }
}
