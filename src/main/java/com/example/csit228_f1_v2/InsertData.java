package com.example.csit228_f1_v2;

import java.sql.*;

public class InsertData {
//    public static void main(String[] args) {
    public static boolean insertData(String username, String password){
        int ctr = 0;
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO users (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ){
//            String name = "Nina";
//            String email = "ninz@gmail.com";
            statement.setString(1, username);
            statement.setString(2, password);
            ctr = statement.executeUpdate();
            System.out.println("Inserted " + ctr + " data successfully");
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                HelloApplication.current_uid = rs.getInt(1);
            }
            System.out.println("current user id: " + HelloApplication.current_uid);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return (ctr > 0);
    }

    public static void addNote(String title, String contents){
        try (
            Connection c = MySQLConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(
              "INSERT INTO notes (uid, title, contents) VALUES (?, ?, ?)"
            );
        ){
            ps.setInt(1,HelloApplication.current_uid);
            ps.setString(2, title);
            ps.setString(3, contents);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
