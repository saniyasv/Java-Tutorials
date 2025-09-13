import java.util.*;  // ✅ All imports at the top

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String msg) {
        super(msg);
    }
}

class InvalidTransactionFormatException extends RuntimeException {
    public InvalidTransactionFormatException(String msg) {
        super(msg);
    }
}

class Account {
    String accountId;
    double balance;

    Account(String accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance += amount;
    }

    void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (balance < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amount;
    }
}

class TransactionProcessor {
    public static void process(String[] transactions, Map<String, Account> accounts) {
        int caseNo = 1;
        for (String transaction : transactions) {
            try {
                System.out.println("\nCase " + caseNo + ": " + transaction);
                caseNo++;

                String[] parts = transaction.split(",");
                if (parts.length != 3) {
                    throw new InvalidTransactionFormatException("Invalid format");
                }

                String accountId = parts[0].trim();
                String operation = parts[1].trim().toUpperCase();
                double amount = Double.parseDouble(parts[2].trim());

                Account acc = accounts.get(accountId);
                if (acc == null) {
                    System.out.println("Error: Account not found");
                    continue;
                }

                if (operation.equals("DEPOSIT")) {
                    acc.deposit(amount);
                    System.out.println("Deposit successful. New balance: " + acc.balance);
                } else if (operation.equals("WITHDRAW")) {
                    try {
                        acc.withdraw(amount);
                        System.out.println("Withdraw successful. New balance: " + acc.balance);
                    } catch (InsufficientFundsException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    throw new InvalidTransactionFormatException("Invalid operation");
                }

            } catch (InvalidTransactionFormatException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid amount format");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

public class ACC {
    public static void main(String[] args) {
        Map<String, Account> accounts = new HashMap<>();
        accounts.put("A1001", new Account("A1001", 500.0));
        accounts.put("A1002", new Account("A1002", 100.0));

        String[] transactions = {
            "A1001,DEPOSIT,200.00",
            "A1002,WITHDRAW,50.00",
            "A1001,WITHDRAW,800.00",
            "A1003,DEPOSIT,abc",
            "A1002,TRANSFER,10",
            "badformatline",
            "A1002,WITHDRAW,-20"
        };

        TransactionProcessor.process(transactions, accounts);

        System.out.println("\nFinal Balances:");
        for (Account acc : accounts.values()) {
            System.out.println(acc.accountId + ": " + acc.balance);
        }
    }
}
