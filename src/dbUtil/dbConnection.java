package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnection {
    public static Connection getConnection() throws Exception{
        try {
            String url = "jdbc:mysql://localhost:3306/demo";
            String username = "root";
            String password = "matematika269";

            Connection conn = DriverManager.getConnection(url, username, password);
            // System.out.println("Connected.");
            return conn;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
