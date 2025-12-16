package service;

import model.Transaction;
import model.TransactionType;

import java.util.List;

public class BalanceCalculator {
    public int calculateBalance(List<Transaction> transactions) {
        int balance = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.DEPOSIT) {
                balance += transaction.getAmount();
            } else {
                balance -= transaction.getAmount();
            }
        }

        return balance;
    }

    public List<Integer> calculateRunningBalances(List<Transaction> transactions) {
        List<Integer> balances = new java.util.ArrayList<>();
        int balance = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.DEPOSIT) {
                balance += transaction.getAmount();
            } else {
                balance -= transaction.getAmount();
            }
            balances.add(balance);
        }

        return balances;
    }
}
