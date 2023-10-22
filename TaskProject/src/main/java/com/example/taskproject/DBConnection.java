
package com.example.taskproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//DB Connection class makes easier the connection from controllers because just need to use a simple object.
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/task_db";
    private static final String USER = "root";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER,null);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
