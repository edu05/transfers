package org.example.transfersv2;

import java.util.UUID;

public class Account {

    private final UUID id = UUID.randomUUID();

    private int amount = 0;
    private int preparedTransferAmount = 0;

    public PrepareTransferResult prepareTransfer(int transferAmount) {
        //assumes amount different than 0

        //ideally positive transfers would go straight but atm it won't work cos if two positive transfers are sent
        //one might override the next, could set up a list though, and only one negative amount is allowed
        //if u do have a list though and u hit revert u need to know which to revert
        //probably easiest to lock entire account if a transfer is going on
        //could be done by passing an id to the commit and rollback things
        //so atm this has a bug
//        if (transferAmount > 0) {
//            this.preparedTransferAmount = transferAmount;
//            return PrepareTransferResult.READY;
//        }

        if (preparedTransferAmount != 0) {
            return PrepareTransferResult.WITHDRAWAL_IN_FLIGHT;
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
