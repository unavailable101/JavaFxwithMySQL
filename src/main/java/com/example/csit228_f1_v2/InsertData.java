package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {
//    public static void main(String[] args) {
    public static boolean insertData(String username, String password){
        int ctr = 0;
        try( Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO users (username, password) VALUES (?, ?)");
        ){
//            String name = "Nina";
//            String email = "ninz@gmail.com";
            statement.setString(1, username);
            statement.setString(2, password);
            ctr = statement.executeUpdate();
            System.out.println("Inserted " + ctr + " data successfully");

        } catch (SQLException e){
            e.printStackTrace();
        }
        return (ctr > 0);
    }
}
