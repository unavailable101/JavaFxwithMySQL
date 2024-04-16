package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteData {
//    public static void main(String[] args) {
    public static void deleteData(){
        try (
                Connection c = MySQLConnection.getConnection();
                PreparedStatement statement = c.prepareStatement(
                        "DELETE FROM users WHERE id>? AND id<?"
                );
        ){

            int starting_id = 2;
            int ending_id = 10;
            statement.setInt(1, starting_id);
            statement.setInt(2, ending_id);
            int ctr = statement.executeUpdate();
            System.out.println("Deleted rows: " + ctr);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
