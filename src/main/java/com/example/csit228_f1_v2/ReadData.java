package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadData {
    //    public static void main(String[] args) {
//    private static String username;
//
//    private static String password;
//    public ReadData() {
//        this.username = null;
//        this.password = null;
//    }
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
//                String username = res.getString("username");
//                String password = res.getString("password");
                if ( username.equals(res.getString("username"))) {
                    curr_username = res.getString("username");
                    return true;
                }
//                System.out.println("ID: " + id + "\t\tName: " + username + "\t\tEmail: " + password);
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
//    }
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
//                String username = res.getString("username");
//                String password = res.getString("password");
                if (curr_username.equals(res.getString("username"))){
                    if (password.equals(res.getString("password"))) {
                        HelloApplication.current_uid = res.getInt("id");
                        return true;
                    }
                }
//                System.out.println("ID: " + id + "\t\tName: " + username + "\t\tEmail: " + password);
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
//    }
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
//        finally {
//            try{
//                c.close();
//                s.close();
//                res.close();
//            } catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
        return null;
    }
}
