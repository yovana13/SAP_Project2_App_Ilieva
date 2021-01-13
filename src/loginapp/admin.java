package loginapp;

import dbUtil.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static dbUtil.dbConnection.getConnection;

public class admin implements Initializable {

    @FXML
    private Label infolabel;
    @FXML
    private Label saletable;
    @FXML
    private TextField deleteproductid;
    @FXML
    private TextField deleteuserid;
    @FXML
    private TextField idproduct;
    @FXML
    private TextField productname;
    @FXML
    private TextField quantity;
    @FXML
    private TextField priceperitem;
    @FXML
    private TextField iduser;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField role;
    @FXML
    private TextField fullname;
    @FXML
    private TextField email;
    @FXML
    private TextField idrepresentative;
    @FXML
    private TextField startdate;
    @FXML
    private TextField enddate;
    @FXML
    private Label producttable;

    private dbConnection dconn;
    public void initialize(URL url, ResourceBundle rs){}


@FXML
public void deleteProduct() throws Exception{
    try {
    if(checkId("products",Integer.parseInt(this.deleteproductid.getText()))) {
    String sqlText = String.format("DELETE FROM products WHERE id=%d", Integer.parseInt(this.deleteproductid.getText()));
    addUpdateDeleteElement(sqlText);
    this.infolabel.setText("Product Successfully Deleted");
    } else {
        this.infolabel.setText("This id does not exists");
    }
    } catch (NumberFormatException e){
        this.infolabel.setText("Input number(integer)");
    }
}

@FXML
public void deleteUser() throws Exception{
        try{
    if(checkId("users",Integer.parseInt(this.deleteuserid.getText()))) {
        String sqlText = String.format("DELETE FROM users WHERE id=%d", Integer.parseInt(this.deleteuserid.getText()));
        addUpdateDeleteElement(sqlText);
    this.infolabel.setText("User Successfully Deleted");
    } else {
        this.infolabel.setText("This id does not exists");
    }
        } catch (NumberFormatException e){
            this.infolabel.setText("Input number(integer)");
        }
}

@FXML
public void addProduct() throws Exception {
        try {
    if(checkId("products",Integer.parseInt(this.idproduct.getText()))!=true) {
        String sqlText = String.format("INSERT INTO products values(%d,'%s',%d,%s)", Integer.parseInt(this.idproduct.getText()), this.productname.getText(), Integer.parseInt(this.quantity.getText()), this.priceperitem.getText());
        addUpdateDeleteElement(sqlText);
        this.infolabel.setText("Product Successfully Added");
    } else {
        this.infolabel.setText("Duplicate entry for id, id already exists");
        }
} catch (NumberFormatException e){
        this.infolabel.setText("Invalid input");
    }
}

@FXML
public void editProduct() throws Exception{
        try {
    if(checkId("products",Integer.parseInt(this.idproduct.getText()))) {
    String sqlText = String.format("UPDATE products SET name='%s', quantity=%d, pricePerItem=%s WHERE id=%s", this.productname.getText(), Integer.parseInt(this.quantity.getText()),this.priceperitem.getText() , Integer.parseInt(this.idproduct.getText()));
    addUpdateDeleteElement(sqlText);
    this.infolabel.setText("Product Successfully Edited");
    } else {
        this.infolabel.setText("This id does not exists");
    }
        } catch (NumberFormatException e){
            this.infolabel.setText("Invalid input");
        }
}

@FXML
public void addUser() throws Exception{
        try{
    if(checkId("users",Integer.parseInt(this.iduser.getText()))!=true) {
    String sqlText = String.format("INSERT INTO users values(%d,'%s','%s','%s','%s','%s')", Integer.parseInt(this.iduser.getText()), this.username.getText(), this.password.getText(),this.role.getText(),this.fullname.getText(),this.email.getText());
    addUpdateDeleteElement(sqlText);
    this.infolabel.setText("User Successfully Added");
    } else {
        this.infolabel.setText("Duplicate entry for id, id already exists");
    }
} catch (NumberFormatException e){
        this.infolabel.setText("Invalid input");
    }
}

@FXML
public void editUser() throws Exception{
        try{
    if(checkId("users",Integer.parseInt(this.iduser.getText()))) {
    String sqlText = String.format("UPDATE users SET username='%s', password='%s',role='%s',fullname='%s',email='%s' WHERE id=%d", this.username.getText(),this.password.getText(),this.role.getText(),this.fullname.getText(),this.email.getText(),Integer.parseInt(this.iduser.getText()) );
    addUpdateDeleteElement(sqlText);
    this.infolabel.setText("User Successfully Edited");
    } else {
        this.infolabel.setText("This id does not exists");
    }
} catch (NumberFormatException e){
        this.infolabel.setText("Invalid input");
    }
}

@FXML
public void saleOfRepresentative() throws Exception {
    try {
        String result="";
        Connection myConn = getConnection();
        Statement myStmt = myConn.createStatement();
        String sql = String.format("SELECT * FROM sales WHERE soldBy=%d",Integer.parseInt(this.idrepresentative.getText()));
        ResultSet rs = myStmt.executeQuery(sql);

        while (rs.next()) {
            result=result+String.format("Sale id: "+rs.getString("saleId") + " Id of the sold product: " + rs.getString("idProduct") + " Quantity sold: "+rs.getString("quantitySold")+" Date sold: "+rs.getString("dateSold")+" Total price from the sale: "+rs.getString("totalPrice"));
            result=result+"\n";
        }
        this.saletable.setText(result);
        this.infolabel.setText("Table Successfully Printed");
} catch (NumberFormatException e){
        this.infolabel.setText("Input number(integer)");
    }

}

@FXML
public void periodReview() throws SQLException {
    try {
        String result="";
        Connection myConn = getConnection();
        Statement myStmt = myConn.createStatement();
        String date = this.startdate.getText();
        String []dateArray = new String[3];
        while (!date.equals(this.enddate.getText())){
            String sql = String.format("SELECT * FROM sales WHERE dateSold='%s'",date);
            ResultSet rs = myStmt.executeQuery(sql);

            while (rs.next()) {
                result=result+String.format("Sale id: "+rs.getString("saleId") + " Id of the sold product: " + rs.getString("idProduct") +" Sold by: "+rs.getString("soldBy")+ " Quantity sold: "+rs.getString("quantitySold")+" Date sold: "+rs.getString("dateSold"));
                result=result+"\n";
            }
            dateArray=date.split("\\.");
            if(Integer.parseInt(dateArray[0])<31){
                if(Integer.parseInt(dateArray[0])<9)
                    dateArray[0] = "0"+String.valueOf(Integer.parseInt(dateArray[0])+1);
                else
                    dateArray[0] = String.valueOf(Integer.parseInt(dateArray[0])+1);
            }
            else if(Integer.parseInt(dateArray[1])<12){
                dateArray[0]= "01";
                if(Integer.parseInt(dateArray[1])<9)
                    dateArray[1] = "0"+String.valueOf(Integer.parseInt(dateArray[1])+1);
                else
                    dateArray[1] = String.valueOf(Integer.parseInt(dateArray[1])+1);
            }
            else {
                dateArray[0]= "01";
                dateArray[1] = "01";
                dateArray[2] = String.valueOf(Integer.parseInt(dateArray[2])+1);
            }
            date = String.join(".",dateArray);
        }
        this.saletable.setText(result);

        this.infolabel.setText("Table Successfully Printed");
    } catch (SQLException e) {
        this.infolabel.setText("SQLException found in the code");
    }

}

    @FXML
    public void printProductTable(ActionEvent event1)throws  SQLException{

        try {
            String result="";
            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT * FROM products";
            ResultSet rs = myStmt.executeQuery(sql);

            while (rs.next()) {
                result=result+String.format("Product id: "+rs.getInt("id") + " Name of product: " + rs.getString("name")+" Quantity: "+rs.getInt("quantity")+ " Price per item: "+rs.getFloat("pricePerItem"));
                result=result+"\n";
            }
            this.producttable.setText(result);

        } catch (SQLException e) {
            this.infolabel.setText("SQLException found in the code");
        }
    }

public  void addUpdateDeleteElement(String sqlText) throws SQLException{
        try {
            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            myStmt.executeUpdate(sqlText);

        } catch (SQLException e) {
            this.infolabel.setText("SQLException found in the code");
        }
    }

    public  boolean checkId(String table, int id){
        int result=0;
        try {

            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE id=%d",table,id);

            ResultSet rs = myStmt.executeQuery(sql);
            while (rs.next()) {
                result = rs.getInt("id");
                if(result==id)
                    return true;
            }
            return false;
        } catch (SQLException e) {
            this.infolabel.setText("SQLException found in the code");
        }
        return false;
    }

}
