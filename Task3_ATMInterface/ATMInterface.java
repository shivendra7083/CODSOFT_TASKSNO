import java.util.Scanner;

/** CodSoft Task 3: ATM Interface. */
public class ATMInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = new BankAccount(10000.00);
        ATM atm = new ATM(account);
        atm.run(scanner);
        scanner.close();
    }
}

class BankAccount {
    private double balance;

    BankAccount(double openingBalance) {
        if (openingBalance < 0) throw new IllegalArgumentException("Opening balance cannot be negative.");
        balance = openingBalance;
    }

    double getBalance() {
        return balance;
    }

    boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }

    boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        return true;
    }
}

class ATM {
    private final BankAccount account;

    ATM(BankAccount account) {
        this.account = account;
    }

    void run(Scanner scanner) {
        System.out.println("=================================");
        System.out.println("            ATM INTERFACE");
        System.out.println("=================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(scanner, "Choose an option: ");

            switch (choice) {
                case 1 -> checkBalance();
                case 2 -> deposit(scanner);
                case 3 -> withdraw(scanner);
                case 4 -> {
                    running = false;
                    System.out.println("Thank you for using the ATM.");
                }
                default -> System.out.println("Invalid option. Please choose 1-4.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
    }

    private void checkBalance() {
        System.out.printf("Current balance: ₹%.2f%n", account.getBalance());
    }

    private void deposit(Scanner scanner) {
        double amount = readAmount(scanner, "Enter amount to deposit: ");
        if (account.deposit(amount)) {
            System.out.printf("₹%.2f deposited successfully.%n", amount);
            checkBalance();
        } else {
            System.out.println("Deposit amount must be greater than zero.");
        }
    }

    private void withdraw(Scanner scanner) {
        double amount = readAmount(scanner, "Enter amount to withdraw: ");
        if (amount > account.getBalance()) {
            System.out.println("Transaction failed: insufficient balance.");
        } else if (account.withdraw(amount)) {
            System.out.printf("₹%.2f withdrawn successfully.%n", amount);
            checkBalance();
        } else {
            System.out.println("Withdrawal amount must be greater than zero.");
        }
    }

    private int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) return scanner.nextInt();
            System.out.println("Please enter a valid integer.");
            scanner.next();
        }
    }

    private double readAmount(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double amount = scanner.nextDouble();
                if (amount > 0) return amount;
            } else {
                scanner.next();
            }
            System.out.println("Please enter a valid amount greater than zero.");
        }
    }
}
