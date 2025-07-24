# Bank Management System (Java + MySQL)

A simple console-based Bank Management System built using Java and MySQL. It supports basic banking operations like account creation, deposit, withdrawal, and account detail viewing.

## 💡 Features
- Create account
- Deposit money
- Withdraw money
- View account details

## 🛠 Tech Stack
- Java
- MySQL
- JDBC

## 🗄 Database Setup
1. Import the `bankdb.sql` file into your MySQL server.
2. Update the DB credentials in `BankManagementSystem.java` (`USER`, `PASSWORD`, and `URL`).

## ▶️ How to Run

### Compile:
```bash
javac -cp .;mysql-connector-java.jar src/BankManagementSystem.java
```

### Run:
```bash
java -cp .;mysql-connector-java.jar src.BankManagementSystem
```

*(Use `:` instead of `;` on Linux/Mac)*
