package database;
import java.sql.*;

public class DatabaseConnection
{
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0553830849Aa";

    public static Connection connect()
    {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection(URL,USERNAME,PASSWORD);
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                return  null;
            }catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                return  null;
            }
        }
    }
