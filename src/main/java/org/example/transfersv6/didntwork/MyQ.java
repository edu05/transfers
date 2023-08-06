package org.example.transfersv6.didntwork;

import org.example.transfersv6.Transfer;

public class MyQ {

    public static final int SIZE = 200_000;
    private final Transfer[] transfers = new Transfer[SIZE];
    private int writeIndex = 0;
    private int readIndex= 0;
    public void add(Transfer transfer) {
        transfers[writeIndex] = transfer;
        writeIndex = (writeIndex + 1) % SIZE;
    }

    public Transfer poll() {
        Transfer transfer = transfers[readIndex];
        if (transfer != null) {
            readIndex = (readIndex + 1) % SIZE;
        }
        return transfer;
    }
}
