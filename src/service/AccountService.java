package service;

import model.Transaction;
import model.TransactionType;
import repository.TransactionRepository;
import validation.TransactionValidator;

import java.time.LocalDate;
import java.util.List;

public class AccountService {
    private final TransactionRepository repository;
    private final BalanceCalculator balanceCalculator;

    public AccountService(TransactionRepository repository) {
        this.repository = repository;
        this.balanceCalculator = new BalanceCalculator();
    }

    public void deposit(int amount, LocalDate date) {
        TransactionValidator.validateTransaction(amount, date, "Deposit");

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount, date);
        repository.save(transaction);
    }

    public void withdraw(int amount, LocalDate date) {
        TransactionValidator.validateTransaction(amount, date, "Withdrawal");

        Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, amount, date);
        repository.save(transaction);
    }

    public int getBalance() {
        List<Transaction> transactions = repository.findAll();
        return balanceCalculator.calculateBalance(transactions);
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }
}
