package repository;


import entity.Doctor;

import java.sql.*;
import java.util.ArrayList;

public class ConnectDb {

    private static final String url = "jdbc:postgresql://localhost:5432/hospital";
    private static final String user = "postgres";
    private static final String password = "test";

    private static ConnectDb connectDb ;
    private static Connection connection ;

    static {
        connectDb = null ;
    }

    private ConnectDb () {

    }

    public Connection getConnection(){
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user,password);
            } catch (SQLException e) {
                System.out.println("Bağlantı sırasında bir hata oluştu: " + e.getMessage());
            }
        }
        return connection;
    }

    public static ConnectDb getInstance () {
        if (connectDb == null) {
            connectDb = new ConnectDb() ;
        }
        return connectDb ;
    }

}
