package loginapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dbUtil.dbConnection.getConnection;

public class LoginModel {
    Connection connection;

    public LoginModel() throws Exception {
        try {
            this.connection = getConnection();
        }catch (SQLException e){
            System.out.println(e);
        }
        if(this.connection==null){
            System.exit(1);
        }
    }

    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String user, String password) throws Exception{
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
        } catch (Exception e) {
            return false;
        }
    }

    public String getRole(String username) throws Exception{
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
