package org.example.transfersv3;

public class TransferProcessor implements Actor {

    public static final TransferProcessor INSTANCE = new TransferProcessor();

    private TransferProcessor() {
    }

    @Override
    public void processMessage(Message message) {

    }

    public void queue() {

    }
}
