package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
//    public static void main(String[] args) {

    public static void createUser(){
        Connection c =  MySQLConnection.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "username VARCHAR(50) NOT NULL," +
                "password VARCHAR(100) NOT NULL)";
        try {
            Statement statement = c.createStatement();
            statement.execute(query);
            System.out.println("Table 'users' has been created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void createNote(){
        try (Connection c =  MySQLConnection.getConnection();) {
            String query = "CREATE TABLE IF NOT EXISTS notes (" +
                    "uid INT," +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "title VARCHAR(100) NOT NULL DEFAULT 'no title'," +
                    "contents VARCHAR(4294967295), " +
                    "FOREIGN KEY (uid) REFERENCES users(id) )";
            Statement statement = c.createStatement();
            statement.execute(query);
            System.out.println("Table 'notes' has been created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
