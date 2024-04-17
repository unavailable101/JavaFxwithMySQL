package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {
//    public static void main(String[] args) {
    public void updateData(){
        try (
                Connection c = MySQLConnection.getConnection();
                PreparedStatement statement = c.prepareStatement(
                        "UPDATE users SET name=? WHERE id=?"
                );
        ){

//            String new_name = "Margarette";
            int id = 1;
//            statement.setString(1,new_name);
//            statement.setInt(2,id);
            int updates = statement.executeUpdate();
            System.out.println("Rows updated: " + updates);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
