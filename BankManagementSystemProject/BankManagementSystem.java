import java.sql.*;
import java.util.Scanner;

class BankManagementSystem {
    static boolean status = false;
    static final String URL = "jdbc:mysql://localhost:3306/bankdb";
    static final String USER = "root";
    static final String PASSWORD = "0310";

    static Connection conn;

    static class Customer {
        String name;
        String accountType;
        int accNo;
        int balance = 0;
        int initialDeposit;

        void createAccount() {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Account Number: ");
            accNo = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            name = sc.nextLine();

            System.out.print("Enter Account Type (Saving/Current): ");
            accountType = sc.nextLine();

            System.out.print("Enter Initial Deposit: ");
            initialDeposit = sc.nextInt();

            // ✅ Input Validation
            if (!accountType.equalsIgnoreCase("Saving") && !accountType.equalsIgnoreCase("Current")) {
                System.out.println("Invalid account type. Must be 'Saving' or 'Current'.");
                return;
            }

            if (initialDeposit < 0) {
                System.out.println("Initial deposit cannot be negative.");
                return;
            }

            if (accountType.equalsIgnoreCase("Saving") && initialDeposit < 1000) {
                System.out.println("Minimum ₹1000 required to open a savings account.");
                return;
            }

            try {
                PreparedStatement check = conn.prepareStatement("SELECT acc_no FROM customers WHERE acc_no = ?");
                check.setInt(1, accNo);
                ResultSet rs = check.executeQuery();
                if (rs.next()) {
                    System.out.println("Account number already exists.");
                    return;
                }

                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO customers (acc_no, name, account_type, balance) VALUES (?, ?, ?, ?)");
                ps.setInt(1, accNo);
                ps.setString(2, name);
                ps.setString(3, accountType);
                ps.setInt(4, initialDeposit);
                ps.executeUpdate();

                System.out.println("Account created successfully.\n");
            } catch (Exception e) {
                System.out.println("Connection Error: " + e.getMessage());
            }
        }
    }

    static void depositMoney() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();
        System.out.print("Enter deposit amount: ");
        int deposit = sc.nextInt();

        if (deposit <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT balance FROM customers WHERE acc_no = " + accNo);

            if (rs.next()) {
                int current = rs.getInt(1);
                int newBal = current + deposit;

                PreparedStatement ps = conn.prepareStatement("UPDATE customers SET balance = ? WHERE acc_no = ?");
                ps.setInt(1, newBal);
                ps.setInt(2, accNo);
                ps.executeUpdate();

                System.out.println("Deposit successful.\n");
            } else {
                System.out.println("Account not found.\n");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void withdrawMoney() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();
        System.out.print("Enter withdraw amount: ");
        int amount = sc.nextInt();

        if (amount <= 0) {
            System.out.println("Withdraw amount must be greater than zero.");
            return;
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT balance, account_type FROM customers WHERE acc_no = " + accNo);

            if (rs.next()) {
                int current = rs.getInt("balance");
                String accType = rs.getString("account_type");

                int newBal = current - amount;

                if (current < amount) {
                    System.out.println("Insufficient balance.");
                    return;
                }

                if (accType.equalsIgnoreCase("Saving") && newBal < 1000) {
                    System.out.println("Cannot withdraw. Savings account must maintain ₹1000 minimum.");
                    return;
                }

                PreparedStatement ps = conn.prepareStatement("UPDATE customers SET balance = ? WHERE acc_no = ?");
                ps.setInt(1, newBal);
                ps.setInt(2, accNo);
                ps.executeUpdate();

                System.out.println("Withdraw successful.\n");
            } else {
                System.out.println("Account not found.\n");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void checkBalance() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name, account_type, balance FROM customers WHERE acc_no = " + accNo);

            if (rs.next()) {
                System.out.println("Account Holder Name: " + rs.getString(1));
                System.out.println("Account Type: " + rs.getString(2));
                System.out.println("Balance: " + rs.getInt(3));
            } else {
                System.out.println("Account not found.\n");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void listAllCustomers() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

            System.out.printf("%-10s %-20s %-15s %-10s\n", "Acc No", "Name", "Account Type", "Balance");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-15s %-10d\n",
                        rs.getInt("acc_no"),
                        rs.getString("name"),
                        rs.getString("account_type"),
                        rs.getInt("balance"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter account number to delete: ");
        int accNo = sc.nextInt();

        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM customers WHERE acc_no = ?");
            ps.setInt(1, accNo);
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Account deleted successfully.");
            else
                System.out.println("Account not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            status = true;
        } catch (Exception e) {
            System.out.println("Database Connection Failed: " + e.getMessage());
        }

        if (status) {
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("\n1. Create Account");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Check Balance");
                System.out.println("5. Delete Account");
                System.out.println("6. List All Customers");
                System.out.println("7. Exit");

                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: new Customer().createAccount(); break;
                    case 2: depositMoney(); break;
                    case 3: withdrawMoney(); break;
                    case 4: checkBalance(); break;
                    case 5: deleteAccount(); break;
                    case 6: listAllCustomers(); break;
                    case 7:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice.\n");
                }
            }
        }
    }
}
