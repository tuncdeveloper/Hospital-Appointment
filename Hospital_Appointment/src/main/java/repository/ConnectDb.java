package repository;
import java.sql.*;

public class ConnectDb {

    private static final String url = "jdbc:postgresql://localhost:5432/hospital";
    private static final String user = "postgres";
    private static final String password = "test";

    private static ConnectDb connectDb ;
    private static Connection connection ;



    private ConnectDb () {

    }

    public Connection getConnection(){
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    connection = DriverManager.getConnection(url, user,password);
                } catch (SQLException e) {
                    System.out.println("Bağlantı sırasında bir hata oluştu: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
