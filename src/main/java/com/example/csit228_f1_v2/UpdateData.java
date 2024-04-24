package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {
    public static void updatePassword(int id, String newPass){
        Connection c = null;
        try {
            c = MySQLConnection.getConnection();
            c.setAutoCommit(false);

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE users SET password=? WHERE id=?"
            );
            statement.setString(1,newPass);
            statement.setInt(2,id);

            int updates = statement.executeUpdate();
            System.out.println("Rows updated (Password): " + updates);
            c.commit();
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
    }
    public static void updateUsername(int id, String newUsername){
        Connection c = null;
        try {
            c = MySQLConnection.getConnection();
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(
                    "UPDATE users SET username=? WHERE id=?"
            );
            statement.setString(1,newUsername);
            statement.setInt(2,id);

            int updates = statement.executeUpdate();
            System.out.println("Rows updated (Password): " + updates);
            c.commit();
        } catch (SQLException  e){
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
    }
    public static void updateNote(int note_id, String new_title, String new_contents){
        Connection c = null;
        try {
            c = MySQLConnection.getConnection();
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(
                    "UPDATE notes SET title=?, contents=? WHERE id=?"
            );
            statement.setString(1,new_title);
            statement.setString(2,new_contents);
            statement.setInt(3,note_id);

            int updates = statement.executeUpdate();
            System.out.println("Rows updated (Note): " + updates);
            c.commit();
        } catch (SQLException  e){
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

    }
}
