package util;
import model.StatementLine;

import java.time.format.DateTimeFormatter;
public class StatementFormatter {
    private static final String HEADER = "date || credit || debit || balance";
    private static final String SEPARATOR = " || ";
    private final DateTimeFormatter dateFormatter;

    public StatementFormatter() {
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public StatementFormatter(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    public String formatLine(StatementLine line) {
        String date = line.getDate().format(dateFormatter);
        String credit = line.getCredit() != null ? String.valueOf(line.getCredit()) : "";
        String debit = line.getDebit() != null ? String.valueOf(line.getDebit()) : "";
        String balance = String.valueOf(line.getBalance());

        return date + SEPARATOR + credit + SEPARATOR + debit + SEPARATOR + balance;
    }

    public String getHeader() {
        return HEADER;
    }
}
