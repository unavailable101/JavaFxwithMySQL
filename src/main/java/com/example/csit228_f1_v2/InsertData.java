package com.example.csit228_f1_v2;

import java.sql.*;

public class InsertData {
//    public static void main(String[] args) {
    public static boolean insertData(String username, String password){
        int ctr = 0;
        Connection c = null;
        try {
//            String name = "Nina";
//            String email = "ninz@gmail.com";
            c = MySQLConnection.getConnection();
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO users (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, username);
            statement.setString(2, password);
            ctr = statement.executeUpdate();
            System.out.println("Inserted " + ctr + " data successfully");
            ResultSet rs = statement.getGeneratedKeys();

            c.commit();

            if (rs.next()) {
                HelloApplication.current_uid = rs.getInt(1);
            }
            System.out.println("current user id: " + HelloApplication.current_uid);

        } catch (SQLException e){
            e.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException r){
                r.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
        return (ctr > 0);
    }

    public static void addNote(String title, String contents){
        Connection c = null;
        try {
            c = MySQLConnection.getConnection();
            c.setAutoCommit(false);

            PreparedStatement ps = c.prepareStatement(
              "INSERT INTO notes (uid, title, contents) VALUES (?, ?, ?)"
            );

            ps.setInt(1,HelloApplication.current_uid);
            ps.setString(2, title);
            ps.setString(3, contents);
            ps.executeUpdate();

            c.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
//            try {
//                c.rollback();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
        } finally {
            try {
                c.close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }
}
