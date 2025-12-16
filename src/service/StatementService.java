package service;

import model.StatementLine;
import model.Transaction;
import model.TransactionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatementService {
    private final BalanceCalculator balanceCalculator;

    public StatementService() {
        this.balanceCalculator = new BalanceCalculator();
    }

    public List<StatementLine> generateStatementLines(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return new ArrayList<>();
        }

        List<Transaction> sortedTransactions = new ArrayList<>(transactions);
        Collections.sort(sortedTransactions,
                (t1, t2) -> t2.getDate().compareTo(t1.getDate()));

        List<Transaction> chronologicalTransactions = new ArrayList<>(sortedTransactions);
        Collections.reverse(chronologicalTransactions);

        List<Integer> balances = balanceCalculator.calculateRunningBalances(chronologicalTransactions);

        List<StatementLine> statementLines = new ArrayList<>();
        for (int i = 0; i < sortedTransactions.size(); i++) {
            Transaction transaction = sortedTransactions.get(i);
            int balanceIndex = sortedTransactions.size() - 1 - i;
            int balance = balances.get(balanceIndex);

            Integer credit = transaction.getType() == TransactionType.DEPOSIT
                    ? transaction.getAmount() : null;
            Integer debit = transaction.getType() == TransactionType.WITHDRAWAL
                    ? transaction.getAmount() : null;

            statementLines.add(new StatementLine(transaction.getDate(), credit, debit, balance));
        }

        return statementLines;
    }
}
