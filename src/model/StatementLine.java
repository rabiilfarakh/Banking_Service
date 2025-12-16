package model;

import java.time.LocalDate;
import java.util.Objects;

public class StatementLine {
    private final LocalDate date;
    private final Integer credit;
    private final Integer debit;
    private final int balance;

    public StatementLine(LocalDate date, Integer credit, Integer debit, int balance) {
        this.date = date;
        this.credit = credit;
        this.debit = debit;
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getCredit() {
        return credit;
    }

    public Integer getDebit() {
        return debit;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatementLine that = (StatementLine) o;
        return balance == that.balance &&
                Objects.equals(date, that.date) &&
                Objects.equals(credit, that.credit) &&
                Objects.equals(debit, that.debit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, credit, debit, balance);
    }
}
