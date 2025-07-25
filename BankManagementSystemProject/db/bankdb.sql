CREATE DATABASE IF NOT EXISTS bankdb;

USE bankdb;

CREATE TABLE IF NOT EXISTS customers (
    acc_no INT PRIMARY KEY,
    name VARCHAR(50),
    acc_type VARCHAR(20),
    balance INT
);