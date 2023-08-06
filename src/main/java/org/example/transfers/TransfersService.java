package org.example.transfers;

public class TransfersService {

    public void transfer(Account from, Account to, Money transferAmount) {
        Account[] orderedAccounts = from.order(to);
        Account firstAccount = orderedAccounts[0];
        Account secondAccount = orderedAccounts[1];
        firstAccount.getLock().lock(); //a1, a2
        secondAccount.getLock().lock(); //a2, a1
        try {
            firstAccount.withdraw(transferAmount);
            secondAccount.deposit(transferAmount);
        } finally {
            secondAccount.getLock().unlock();
            firstAccount.getLock().unlock();
        }


        //begin
        //read amount1
        //read amount2
        //end

        //java code


        //begin
        //update accounts a where a.id = acc1.id and a.amount = old_amount1 set a.amount = new_amount1
        //update accounts a where a.id = acc2.id and a.amount = old_amount2 set a.amount = new_amount2
        //end

        //what accounts where created today
        //explain -> query
    }
}

//a1, a2    a3, a4
//to has 0

//a1, a2   a2, a1    | a1, a2   a2, a3   a3, a1

//t1 and t2 attempt at the same time a transfer of
//from 0
//to 200
