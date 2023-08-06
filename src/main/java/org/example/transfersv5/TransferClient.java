package org.example.transfersv5;


import org.example.transfersv5.messages.transferclient.TransferAborted;
import org.example.transfersv5.messages.transferclient.TransferCommitted;

public class TransferClient extends Actor {

    private int committedCount = 0;
    private int abortedCount = 0;

    @Override
    protected void process(Object message) {
        if (message instanceof TransferCommitted) {
            process((TransferCommitted) message);
        } else if (message instanceof TransferAborted) {
            process((TransferAborted) message);
        } else {
            throw new RuntimeException("wrong message " + message);
        }
    }

    private void process(TransferCommitted transferCommitted) {
        committedCount++;
//        System.out.println("committed tx " + transferCommitted.id() + ", committed=" + committedCount + ", aborted=" + abortedCount);

    }

    private void process(TransferAborted transferAborted) {
        abortedCount++;
//        System.out.println("aborted tx " + transferAborted.id() + ", committed=" + committedCount + ", aborted=" + abortedCount);
    }
}
