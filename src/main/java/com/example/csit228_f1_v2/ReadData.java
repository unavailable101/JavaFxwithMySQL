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
    public static boolean getUsername(String username) {
        try (
                Connection c = MySQLConnection.getConnection();
                Statement statement = c.createStatement();
        ) {
            String query = "SELECT * FROM users";
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                int id = res.getInt("id");
//                String username = res.getString("username");
//                String password = res.getString("password");
                if ( username.equals(res.getString("username"))) return true;
//                System.out.println("ID: " + id + "\t\tName: " + username + "\t\tEmail: " + password);
            }
        } catch (SQLException e) {

        }
//    }
        return false;
    }

    public static boolean getPassword(String password)  {
        try (
                Connection c = MySQLConnection.getConnection();
                Statement statement = c.createStatement();
        ) {
            String query = "SELECT * FROM users";
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                int id = res.getInt("id");
//                String username = res.getString("username");
//                String password = res.getString("password");
                if ( password.equals(res.getString("password"))) return true;
//                System.out.println("ID: " + id + "\t\tName: " + username + "\t\tEmail: " + password);
            }
        } catch (SQLException e) {

        }
//    }
        return false;
    }

}
