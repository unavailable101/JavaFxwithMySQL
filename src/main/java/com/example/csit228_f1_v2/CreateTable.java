package com.example.csit228_f1_v2;

import java.sql.*;

public class CreateTable {
//    public static void main(String[] args) {
    public static boolean notesTable(){
        try (Connection c = MySQLConnection.getConnection()) {
            DatabaseMetaData metaData = c.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "notes", null);
            return resultSet.next(); // If next() returns true, table exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean usersTable(){
        try (Connection c = MySQLConnection.getConnection()) {
            DatabaseMetaData metaData = c.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "users", new String[]{"TABLE"});
            return resultSet.next() && !resultSet.wasNull();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void createUser(){
        Connection c =  MySQLConnection.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "firstName VARCHAR(50) NOT NULL," +
                "lastName VARCHAR(50) NOT NULL," +
                "username VARCHAR(50) NOT NULL," +
                "email VARCHAR(50) NOT NULL," +
                "password VARCHAR(100) NOT NULL)";
        try {
            c.setAutoCommit(false);
            Statement statement = c.createStatement();
            statement.execute(query);
            System.out.println("Table 'users' has been created successfully!");
            c.commit();
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
        Connection c = null;
        try {
            c = MySQLConnection.getConnection();
            c.setAutoCommit(false);
            String query =
                    "CREATE TABLE IF NOT EXISTS notes (" +
                    "uid INT," +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "title VARCHAR(100) NOT NULL DEFAULT 'no title'," +
                    "contents TEXT (9999999), " +
                    "FOREIGN KEY (uid) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE)";
            Statement statement = c.createStatement();
            statement.execute(query);
            c.commit();
//            notesTable = true;
            System.out.println("Table 'notes' has been created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }
}
