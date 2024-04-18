package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {
//    public static void main(String[] args) {
    public static void updatePassword(int id, String newPass){
        try (
                Connection c = MySQLConnection.getConnection();
                PreparedStatement statement = c.prepareStatement(
                        "UPDATE users SET password=? WHERE id=?"
                );
        ){

//            String new_name = "Margarette";
//            int id = 1;
            statement.setString(1,newPass);
            statement.setInt(2,id);

            int updates = statement.executeUpdate();
            System.out.println("Rows updated (Password): " + updates);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void updateUsername(int id, String newUsername){
        try (
                Connection c = MySQLConnection.getConnection();
                PreparedStatement statement = c.prepareStatement(
                        "UPDATE users SET username=? WHERE id=?"
                );
        ){
            statement.setString(1,newUsername);
            statement.setInt(2,id);

            int updates = statement.executeUpdate();
            System.out.println("Rows updated (Password): " + updates);
        } catch (SQLException  e){
            e.printStackTrace();
        }
    }
}
