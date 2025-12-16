package validation;

import exception.InvalidAmountException;
import exception.InvalidDateException;

import java.time.LocalDate;

public class TransactionValidator {
    private TransactionValidator() {
    }

    public static void validateAmount(int amount, String operationType) {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    operationType + " amount must be positive. Received: " + amount
            );
        }
    }

    public static void validateDate(LocalDate date) {
        if (date == null) {
            throw new InvalidDateException("Transaction date cannot be null");
        }
    }

    public static void validateTransaction(int amount, LocalDate date, String operationType) {
        validateAmount(amount, operationType);
        validateDate(date);
    }
}

