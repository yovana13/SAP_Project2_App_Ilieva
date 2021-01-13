package loginapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dbUtil.dbConnection.getConnection;

public class LoginModel {
    Connection connection;

    public LoginModel() throws SQLException {
        try {
            this.connection = getConnection();
        }catch (SQLException e){
            System.out.println("SQL Exception found in the code");
        }
        if(this.connection==null){
            System.exit(1);
        }
    }

    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String user, String password) throws SQLException{
        String result="";
        try {
            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            String sql = String.format("SELECT * FROM users WHERE username ='%s'",user);
            ResultSet rs = myStmt.executeQuery(sql);

            while (rs.next()) {
                result = rs.getString("password");
                if(result.equals(password))
                    return true;
            }
                return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public String getRole(String username) throws SQLException{
        String result="";
        try {
            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            String sql = String.format("SELECT * FROM users WHERE username='%s'",username);

            ResultSet rs = myStmt.executeQuery(sql);
            while (rs.next()) {
                result = rs.getString("role");
            }
            return result;
        } catch (SQLException e) {
            System.out.println("SQL Exception found in the code, we did not got role");
        }
        return result;
    }
}
