# JavaFX User Cabinet

Desktop application built with JavaFX and MySQL (JDBC) for viewing and updating user profile data.

## Features
- Automatic loading of user profile (Admin) on startup
- Duplicate login validation before updating
- Password hashing using MD5
- Automatic form clearing after successful update

## Tech Stack
- Java 22, JavaFX 22
- Maven
- MySQL / JDBC (mysql-connector-j)

## Database Setup
```sql
CREATE DATABASE itproger_javafx;
USE itproger_javafx;

CREATE TABLE users (
  id INT(11) NOT NULL AUTO_INCREMENT,
  login VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO users (login, email, password) VALUES 
('Admin', 'some@mail.ru', '202cb962ac59075b964b07152d234b70');
```
