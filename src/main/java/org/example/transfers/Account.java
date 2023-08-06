package org.example.transfers;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final UUID id = UUID.randomUUID();
    private String firstName;
    private String lastName;
    private Money money = Money.zero();
    private Lock lock = new ReentrantLock();

    public Account(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Lock getLock() {
        return lock;
    }

    public void withdraw(Money withdrawalAmount) {
        this.money = this.money.subtract(withdrawalAmount);
    }

    public void deposit(Money depositAmount) {
        this.money = this.money.add(depositAmount);
    }

    public Account[] order(Account other) {
        if (id.compareTo(other.id) > 0) {
            return new Account[] {this, other};
        } else {
            return new Account[] {other, this};
        }
    }
}

