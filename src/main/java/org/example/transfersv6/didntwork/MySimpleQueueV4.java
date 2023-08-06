package org.example.transfersv6.didntwork;

import org.example.transfersv6.Transfer;

import java.util.LinkedList;
import java.util.UUID;

public class MySimpleQueueV4 {

    private final SenderAndQueue[] transfers = new SenderAndQueue[100];
    private int index = 0;

    public void add(UUID senderId, Transfer transfer) {
        for (int i = 0; i < index; i++) {
            if (transfers[i] != null) {
                transfers[i].queue().add(transfer);
                return;
            }
        }
        SenderAndQueue senderAndQueue = new SenderAndQueue(senderId, new LinkedList<>());
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
