package com.example.csit228_f1_v2;

import java.sql.*;

public class ReadData {
    private static String curr_username;

    public static boolean getUsername(String username) {
        Connection c = null;
        try{
            c = MySQLConnection.getConnection();
            c.setAutoCommit(false);

            Statement statement = c.createStatement();

            String query = "SELECT * FROM users";
            ResultSet res = statement.executeQuery(query);
            c.commit();
            while (res.next()) {
                int id = res.getInt("id");
                if ( username.equals(res.getString("username"))) {
                    curr_username = res.getString("username");
                    return true;
                }
            }
        } catch (SQLException e) {
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
        return false;
    }

    public static boolean getPassword(String password)  {
        Connection c = null;
        try {
            c = MySQLConnection.getConnection();
            c.setAutoCommit(false);

            Statement statement = c.createStatement();

            String query = "SELECT * FROM users";
            ResultSet res = statement.executeQuery(query);
            c.commit();
            while (res.next()) {
                int id = res.getInt("id");
                if (curr_username.equals(res.getString("username"))){
                    if (password.equals(res.getString("password"))) {
                        HelloApplication.current_uid = res.getInt("id");
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
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
        return false;
    }

    public static ResultSet all_notes(){
        Connection c = null;
        try{
            c = MySQLConnection.getConnection();
            c.setAutoCommit(false);
            Statement s = c.createStatement();
            String query = "SELECT * FROM notes WHERE uid="+HelloApplication.current_uid;
            ResultSet res =  s.executeQuery(query);
            c.commit();
            return res;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet userProfile(){
        Connection c = null;
        try{
            c = MySQLConnection.getConnection();
            c.setAutoCommit(false);
            Statement s = c.createStatement();
            String query = "SELECT firstName, lastName, email FROM users WHERE id="+HelloApplication.current_uid;
            ResultSet res =  s.executeQuery(query);
            c.commit();
            return res;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static String userFullName(){
        try (
            Connection c = MySQLConnection.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT firstName, lastName, email FROM users WHERE id=?");
        ) {
            ps.setInt(1, HelloApplication.current_uid);
            ResultSet res = ps.executeQuery();
            if (res.next()){
                return res.getString("firstName") + " " + res.getString("lastName");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public static int noteCount(){
        if (CreateTable.notesTable()){
            try (
                    Connection c = MySQLConnection.getConnection();
                    PreparedStatement ps = c.prepareStatement(
                            "SELECT COUNT(uid) AS noteCount FROM notes WHERE uid=?"
                    );
            ) {
                ps.setInt(1, HelloApplication.current_uid);
                try (ResultSet res = ps.executeQuery()) {
                    if (res.next()) return res.getInt("noteCount");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }
}
