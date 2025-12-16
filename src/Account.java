import model.StatementLine;
import repository.TransactionRepository;
import service.AccountService;
import service.StatementService;
import util.StatementFormatter;

import java.time.LocalDate;
import java.util.List;

public class Account {
    private final AccountService accountService;
    private final StatementService statementService;
    private final StatementFormatter statementFormatter;

    public Account() {
        TransactionRepository repository = new TransactionRepository();
        this.accountService = new AccountService(repository);
        this.statementService = new StatementService();
        this.statementFormatter = new StatementFormatter();
    }

    public void deposit(int amount, LocalDate date) {
        accountService.deposit(amount, date);
    }

    public void withdraw(int amount, LocalDate date) {
        accountService.withdraw(amount, date);
    }

    public void printStatement() {
        System.out.println(statementFormatter.getHeader());

        List<StatementLine> lines = statementService.generateStatementLines(
                accountService.getAllTransactions()
        );

        for (StatementLine line : lines) {
            System.out.println(statementFormatter.formatLine(line));
        }
    }

    public int getBalance() {
        return accountService.getBalance();
    }
}
