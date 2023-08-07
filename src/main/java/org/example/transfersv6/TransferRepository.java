package org.example.transfersv6;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TransferRepository {

    private long size = 100_000;
    private final Queue<Transfer> queue = new ConcurrentLinkedQueue<>();
    public static final TransferRepository TRANSFER_REPOSITORY = new TransferRepository();

    private TransferRepository() {
//        for (int i = 0; i < size; i++) {
//            queue.add(new Transfer(null, null, 0));
//        }
//        System.out.println("size is " + size);

    }

//    public Transfer create(UUID from, UUID to, int amount) {
//        Transfer freeTransfer = queue.poll();
//        if (freeTransfer == null) {
//            size.addAndGet(size.get());
//            System.out.println("daaaaaaaaamn " + size.get());
//            for (int i = 0; i < size.get(); i++) {
//                queue.add(new Transfer(null, null, 0));
//            }
//            return create(from, to, amount);
//        }
//
//        freeTransfer.from = from;
//        freeTransfer.to = to;
//        freeTransfer.amount = amount;
//        return freeTransfer;
//    }
    public Transfer create(UUID from, UUID to, int amount) {
        Transfer freeTransfer = queue.poll();
        if (freeTransfer == null) {
            return new Transfer(from, to, amount);
        }

        freeTransfer.from = from;
        freeTransfer.to = to;
        freeTransfer.amount = amount;
        return freeTransfer;
    }

    public void recycle(Transfer transfer) {
        queue.add(transfer);
    }
}
