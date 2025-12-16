import exception.InvalidAmountException;
import exception.InvalidDateException;

import java.time.LocalDate;

public class AccountTest {
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  BANKING SERVICE - TEST SUITE");
        System.out.println("═══════════════════════════════════════════════════\n");

        runTest("Acceptance Test Scenario", AccountTest::testAcceptanceScenario);
        runTest("Deposit Validation - Negative Amount", AccountTest::testDepositNegativeAmount);
        runTest("Deposit Validation - Zero Amount", AccountTest::testDepositZeroAmount);
        runTest("Deposit Validation - Null Date", AccountTest::testDepositNullDate);
        runTest("Withdrawal Validation - Negative Amount", AccountTest::testWithdrawNegativeAmount);
        runTest("Withdrawal Validation - Zero Amount", AccountTest::testWithdrawZeroAmount);
        runTest("Empty Statement", AccountTest::testEmptyStatement);
        runTest("Balance Calculation", AccountTest::testBalanceCalculation);
        runTest("Multiple Transactions Same Day", AccountTest::testMultipleTransactionsSameDay);
        runTest("Negative Balance Scenario", AccountTest::testNegativeBalance);
        runTest("Large Transaction Amounts", AccountTest::testLargeTransactions);

        printTestSummary();
    }

    private static void runTest(String testName, Runnable test) {
        totalTests++;
        System.out.println("▶ Test " + totalTests + ": " + testName);
        System.out.println("─".repeat(50));

        try {
            test.run();
            passedTests++;
            System.out.println("✓ PASSED\n");
        } catch (Exception e) {
            failedTests++;
            System.out.println("✗ FAILED: " + e.getMessage());
            e.printStackTrace();
            System.out.println();
        }
    }

    private static void testAcceptanceScenario() {
        Account account = new Account();
        account.deposit(1000, LocalDate.of(2012, 1, 10));
        account.deposit(2000, LocalDate.of(2012, 1, 13));
        account.withdraw(500, LocalDate.of(2012, 1, 14));

        System.out.println("Expected output:");
        System.out.println("date || credit || debit || balance");
        System.out.println("14/01/2012 || || 500 || 2500");
        System.out.println("13/01/2012 || 2000 || || 3000");
        System.out.println("10/01/2012 || 1000 || || 1000");
        System.out.println("\nActual output:");
        account.printStatement();

        assertEqual(2500, account.getBalance(), "Final balance should be 2500");
    }

    private static void testDepositNegativeAmount() {
        Account account = new Account();

        try {
            account.deposit(-100, LocalDate.now());
            throw new AssertionError("Should throw InvalidAmountException");
        } catch (InvalidAmountException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
    }

    private static void testDepositZeroAmount() {
        Account account = new Account();

        try {
            account.deposit(0, LocalDate.now());
            throw new AssertionError("Should throw InvalidAmountException");
        } catch (InvalidAmountException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
    }

    private static void testDepositNullDate() {
        Account account = new Account();

        try {
            account.deposit(100, null);
            throw new AssertionError("Should throw InvalidDateException");
        } catch (InvalidDateException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
    }

    private static void testWithdrawNegativeAmount() {
        Account account = new Account();

        try {
            account.withdraw(-100, LocalDate.now());
            throw new AssertionError("Should throw InvalidAmountException");
        } catch (InvalidAmountException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
    }

    private static void testWithdrawZeroAmount() {
        Account account = new Account();

        try {
            account.withdraw(0, LocalDate.now());
            throw new AssertionError("Should throw InvalidAmountException");
        } catch (InvalidAmountException e) {
            System.out.println("✓ Correctly rejected: " + e.getMessage());
        }
    }

    private static void testEmptyStatement() {
        Account account = new Account();
        System.out.println("Statement for new account:");
        account.printStatement();

        assertEqual(0, account.getBalance(), "Balance should be 0");
    }

    private static void testBalanceCalculation() {
        Account account = new Account();

        account.deposit(1000, LocalDate.of(2024, 1, 1));
        assertEqual(1000, account.getBalance(), "After 1000 deposit");

        account.deposit(500, LocalDate.of(2024, 1, 2));
        assertEqual(1500, account.getBalance(), "After 500 deposit");

        account.withdraw(200, LocalDate.of(2024, 1, 3));
        assertEqual(1300, account.getBalance(), "After 200 withdrawal");

        account.withdraw(1300, LocalDate.of(2024, 1, 4));
        assertEqual(0, account.getBalance(), "After full withdrawal");

        System.out.println("All balance calculations correct!");
    }

    private static void testMultipleTransactionsSameDay() {
        Account account = new Account();
        LocalDate date = LocalDate.of(2024, 1, 1);

        account.deposit(1000, date);
        account.deposit(500, date);
        account.withdraw(200, date);

        assertEqual(1300, account.getBalance(), "Balance after multiple transactions");

        System.out.println("\nStatement:");
        account.printStatement();
    }

    private static void testNegativeBalance() {
        Account account = new Account();
        account.deposit(500, LocalDate.of(2024, 1, 1));
        account.withdraw(800, LocalDate.of(2024, 1, 2));

        assertEqual(-300, account.getBalance(), "Balance with overdraft");

        System.out.println("\nStatement with negative balance:");
        account.printStatement();
    }

    private static void testLargeTransactions() {
        Account account = new Account();
        account.deposit(1000000, LocalDate.of(2024, 1, 1));
        account.withdraw(250000, LocalDate.of(2024, 1, 2));

        assertEqual(750000, account.getBalance(), "Large transaction handling");
        System.out.println("Balance: " + account.getBalance());
    }

    private static void assertEqual(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(
                    message + " - Expected: " + expected + ", Actual: " + actual
            );
        }
    }

    private static void printTestSummary() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  TEST SUMMARY");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("Total Tests:  " + totalTests);
        System.out.println("Passed:       " + passedTests + " ✓");
        System.out.println("Failed:       " + failedTests + (failedTests > 0 ? " ✗" : ""));
        System.out.println("Success Rate: " +
                String.format("%.1f%%", (passedTests * 100.0 / totalTests)));
        System.out.println("═══════════════════════════════════════════════════");
    }

}