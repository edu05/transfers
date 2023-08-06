package org.example.transfersv3;

import org.example.transfersv2.PrepareTransferResult;

import java.util.UUID;

import static org.example.transfersv3.MessageType.TRANSFER_IN_PROGRESS;

public class Account implements Actor {

    private final UUID id = UUID.randomUUID();

    private int amount = 0;
    private int preparedTransferAmount = 0;
    private final TransferProcessor transferProcessor = TransferProcessor.INSTANCE;

    @Override
    public void processMessage(Message message) {
        switch (message.messageType()) {
            case PREPARE_TRANSFER -> prepareTransfer((int) message.payload());
            default -> throw new RuntimeException("not supported");
        }

    }

    private PrepareTransferResult prepareTransfer(int transferAmount) {
        if (preparedTransferAmount != 0) {
//            transferProcessor.queueMessage(new Message(TRANSFER_IN_PROGRESS, ));
        }

        if (amount - transferAmount < 0) {
            return PrepareTransferResult.NOT_ENOUGH_MONEY;
        }

        preparedTransferAmount = transferAmount;
        return PrepareTransferResult.READY;
    }

    public void commitPreparedTransfer() {
        amount += preparedTransferAmount;
        preparedTransferAmount = 0;
    }

    public void revertPreparedTransfer() {
        preparedTransferAmount = 0;
    }

    public UUID getId() {
        return id;
    }
}
