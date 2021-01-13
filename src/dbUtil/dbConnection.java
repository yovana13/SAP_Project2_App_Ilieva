package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    public static Connection getConnection() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/demo";
            String username = "root";
            String password = "matematika269";

            Connection conn = DriverManager.getConnection(url, username, password);

            return conn;
        }catch (SQLException e){
            System.out.println("SQL Exception found in the code");
            //System.out.println(e);
        }
        return null;
    }
}
