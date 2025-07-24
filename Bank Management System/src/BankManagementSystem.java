import java.sql.*;
import java.util.Scanner;

public class BankManagementSystem {
    static final String URL = "jdbc:mysql://localhost:3306/bankdb";
    static final String USER = "root"; // your MySQL username
    static final String PASSWORD = ""; // your MySQL password

    static Connection conn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            int choice;
            do {
                System.out.println("\n=== BANK MANAGEMENT SYSTEM ===");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Display Account Details");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1: createAccount(sc); break;
                    case 2: depositAmount(sc); break;
                    case 3: withdrawAmount(sc); break;
                    case 4: displayAccount(sc); break;
                    case 5: System.out.println("Exiting..."); break;
                    default: System.out.println("Invalid choice!");
                }
            } while (choice != 5);
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Closing Error: " + e.getMessage());
            }
        }
        sc.close();
    }

    static void createAccount(Scanner sc) throws SQLException {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Account Type (Saving/Current): ");
        String type = sc.nextLine();
        System.out.print("Enter Initial Deposit: ");
        int balance = sc.nextInt();

        String sql = "INSERT INTO customers (acc_no, name, acc_type, balance) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, accNo);
        pst.setString(2, name);
        pst.setString(3, type);
        pst.setInt(4, balance);
        int result = pst.executeUpdate();

        if (result > 0) System.out.println("Account Created Successfully!");
        else System.out.println("Failed to Create Account.");
    }

    static void depositAmount(Scanner sc) throws SQLException {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        System.out.print("Enter Amount to Deposit: ");
        int amount = sc.nextInt();

        String sql = "UPDATE customers SET balance = balance + ? WHERE acc_no = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, amount);
        pst.setInt(2, accNo);
        int result = pst.executeUpdate();

        if (result > 0) System.out.println("Amount Deposited Successfully!");
        else System.out.println("Account Not Found.");
    }

    static void withdrawAmount(Scanner sc) throws SQLException {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        System.out.print("Enter Amount to Withdraw: ");
        int amount = sc.nextInt();

        String checkSql = "SELECT balance FROM customers WHERE acc_no = ?";
        PreparedStatement checkPst = conn.prepareStatement(checkSql);
        checkPst.setInt(1, accNo);
        ResultSet rs = checkPst.executeQuery();

        if (rs.next()) {
            int currentBalance = rs.getInt("balance");
            if (currentBalance >= amount) {
                String updateSql = "UPDATE customers SET balance = balance - ? WHERE acc_no = ?";
                PreparedStatement updatePst = conn.prepareStatement(updateSql);
                updatePst.setInt(1, amount);
                updatePst.setInt(2, accNo);
                updatePst.executeUpdate();
                System.out.println("Amount Withdrawn Successfully!");
            } else {
                System.out.println("Insufficient Balance.");
            }
        } else {
            System.out.println("Account Not Found.");
        }
    }

    static void displayAccount(Scanner sc) throws SQLException {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        String sql = "SELECT * FROM customers WHERE acc_no = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, accNo);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            System.out.println("\nAccount Details:");
            System.out.println("Account No : " + rs.getInt("acc_no"));
            System.out.println("Name       : " + rs.getString("name"));
            System.out.println("Account Type: " + rs.getString("acc_type"));
            System.out.println("Balance     : " + rs.getInt("balance"));
        } else {
            System.out.println("Account Not Found.");
        }
    }
}