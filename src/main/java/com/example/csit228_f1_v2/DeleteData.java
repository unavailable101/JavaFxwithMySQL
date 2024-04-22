package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteData {
//    public static void main(String[] args) {
    public static void deleteAccount(int id){
        try (
                Connection c = MySQLConnection.getConnection();
                PreparedStatement statement = c.prepareStatement(
//                        "DELETE FROM users WHERE id>? AND id<?"
                        "DELETE FROM users WHERE id=?"
                );
        ){

//            int starting_id = 2;
//            int ending_id = 10;
            statement.setInt(1, id);
//            statement.setInt(2, ending_id);
            int ctr = statement.executeUpdate();
            System.out.println("Deleted rows: " + ctr);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteNote(int id){
        try (
                Connection c = MySQLConnection.getConnection();
                PreparedStatement statement = c.prepareStatement(
                        "DELETE FROM notes WHERE id=?"
                );
        ){
            statement.setInt(1, id);
            int ctr = statement.executeUpdate();
            System.out.println("Deleted rows: " + ctr);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteAllNotes(int uid){
        try (
                Connection c = MySQLConnection.getConnection();
                PreparedStatement statement = c.prepareStatement(
                        "DELETE FROM notes WHERE uid=?"
                );
        ){
            statement.setInt(1, uid);
            int ctr = statement.executeUpdate();
            System.out.println("Deleted rows: " + ctr);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
