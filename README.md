
# 💳 Bank Management System - Java + MySQL

A beginner-friendly Bank Management System built using Java and MySQL. It allows users to perform essential banking operations such as creating accounts, depositing and withdrawing money, viewing balances, and managing customer information.

---

## 🧰 Tools & Technologies Used

- **Java** (JDK 8 or above)
- **MySQL** (Database)
- **JDBC** (Java Database Connectivity)
- **VS Code / IntelliJ / Eclipse** (IDE)

---

## 🔧 Step-by-Step Setup Instructions

### ✅ Step 1: Install Prerequisites

Ensure the following are installed on your system:

- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [MySQL Workbench (optional)](https://dev.mysql.com/downloads/workbench/)
- [VS Code or any Java IDE](https://code.visualstudio.com/)
- MySQL JDBC Connector (add to your project build path)

---

### 🗄️ Step 2: Setup MySQL Database

1. **Login to MySQL Command Line or MySQL Workbench.**
2. **Create the database and table:**

   ```sql
   CREATE DATABASE bankdb;
   USE bankdb;

   CREATE TABLE customers (
       account_number INT PRIMARY KEY,
       name VARCHAR(100),
       account_type VARCHAR(20),
       balance INT
   );
   ```

---

### 🧾 Step 3: Configure Database Credentials

1. Open `BankManagementSystem.java`.
2. Locate these lines near the top:

   ```java
   static final String URL = "jdbc:mysql://localhost:3306/bankdb";
   static final String USER = "root"; // Replace with your MySQL username
   static final String PASSWORD = "0310"; // Replace with your MySQL password
   ```

3. Replace `"root"` and `"0310"` with your actual MySQL username and password.

---

### 🖥️ Step 4: Compile & Run the Java Program

#### If using VS Code / IntelliJ:

- Open the project folder.
- Right-click on `BankManagementSystem.java` and choose **Run**.

#### If using Command Line:

1. Navigate to the folder containing `BankManagementSystem.java`.
2. Compile the code:

   ```bash
   javac BankManagementSystem.java
   ```

3. Run the program:

   ```bash
   java BankManagementSystem
   ```

---

## 🎯 Key Features

- 👤 Create new customer accounts
- 📄 Display account information
- 💵 Deposit money into account
- 💸 Withdraw money from account
- 🔍 Check account balance
- 🗑️ Delete customer records
- ✏️ Update customer details

---

## 📚 Project Structure

```
BankManagementSystem/
│
├── BankManagementSystem.java   # Main Java source file
└── README.md                   # Project documentation
```

---

## 🔐 Upcoming Features (To-Do)

- ✅ Login system with user authentication
- 🔑 Password hashing using SHA-256 / bcrypt
- 🧾 Transaction history logging
- 🧠 Robust input validation
- ⚠️ Minimum balance rule enforcement
- 📊 Admin dashboard (optional GUI)

---

## 👨‍💻 Author

**Mr ST**  
B.Tech CSE, CV Raman Global University  
Aspiring Cybersecurity & Ethical Hacking Specialist

---

## 🤝 Contributions

Feel free to fork this repo, improve the code, or suggest new features!

> ✨ *If you like this project, give it a ⭐ on GitHub!*
