package loginapp;

import dbUtil.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static dbUtil.dbConnection.getConnection;

public class representative implements Initializable {

    @FXML
    private TextField idsale;
    @FXML
    private TextField idproduct;
    @FXML
    private TextField quantitysold;
    @FXML
    private TextField datesold;
    @FXML
    private TextField idclientsale;
    @FXML
    private Label infolabel;
    @FXML
    private TextField idclient;
    @FXML
    private TextField clientname;
    @FXML
    private TextField email;
    @FXML
    private TextField idclientdelete;
    @FXML
    private Label clienttable1;
    @FXML
    private Label saletable1;
    @FXML
    private TextField userID;


   // Integer usersId = Integer.parseInt(this.userID.getText());
    private dbConnection dconn;

    public representative() throws Exception {
    }

    public void initialize(URL url, ResourceBundle rs){
        this.dconn = new dbConnection();
    }

    @FXML
    public void addClient() throws Exception{
        try{
        if(checkClient(Integer.parseInt(this.idclient.getText()),Integer.parseInt(this.userID.getText()))!=true) {
        String sqlText = String.format("INSERT INTO clients values(%d,'%s',%d,'%s')", Integer.parseInt(this.idclient.getText()), this.clientname.getText(),Integer.parseInt(this.userID.getText()),this.email.getText());
        addUpdateDeleteElement(sqlText);
        this.infolabel.setText("Client Successfully Added");
        } else {
            this.infolabel.setText("Duplicate entry for id, id already exists in your client list");
        }
    } catch (NumberFormatException e){
        this.infolabel.setText("Invalid input or you did not entered your id on the top of the page");
    }
    }

    @FXML
    public void editClient() throws Exception{
        try{
        if(checkClient(Integer.parseInt(this.idclient.getText()),Integer.parseInt(this.userID.getText()))) {
        String sqlText = String.format("UPDATE clients SET name='%s', addedByUsersId=%d, email='%s' WHERE id=%d", this.clientname.getText(), Integer.parseInt(this.userID.getText()), this.email.getText(),Integer.parseInt(this.idclient.getText()));
        addUpdateDeleteElement(sqlText);
        this.infolabel.setText("Client Successfully Edited");
        } else {
            this.infolabel.setText("This client is not added in your list");
        }
    } catch (NumberFormatException e){
        this.infolabel.setText("Invalid input or you did not entered your id on the top of the page");
    }
    }

    @FXML
    public void deleteClient() throws Exception{
        try{
        if(checkClient(Integer.parseInt(this.idclientdelete.getText()),Integer.parseInt(this.userID.getText()))){
        String sqlText = String.format("DELETE FROM clients WHERE id=%d", Integer.parseInt(this.idclientdelete.getText()));
        addUpdateDeleteElement(sqlText);
        this.infolabel.setText("Client Successfully Deleted");
    } else {
        this.infolabel.setText("This client is not added in your list");
    }
    } catch (NumberFormatException e){
        this.infolabel.setText("Invalid input, you need to input number or you did not entered your id on the top of the page");
    }
    }

    @FXML
    public void printSaleTable(ActionEvent event1)throws  SQLException{

        try {
            String result="";
            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT * FROM sales";
            ResultSet rs = myStmt.executeQuery(sql);

            while (rs.next()) {
                //  result=result+String.format("Sale id: "+rs.getString("saleId") + " Id of the sold product: " + rs.getString("idProduct") + "Sold by:"+rs.getString("soldBy")+ "Quantity sold: "+rs.getString("quantitySold")+" Date sold: "+rs.getString("dateSold")+" Total price from the sale: "+rs.getString("totalPrice"));
                result=result+String.format("Sale id: "+rs.getString("saleId") + " Id of the sold product: " + rs.getString("idProduct")+" Sold by: "+rs.getString("soldBy")+ " Quantity sold: "+rs.getString("quantitySold")+" Date sold: "+rs.getString("dateSold")+" Total price from the sale: "+rs.getString("totalPrice"));
                result=result+"\n";
            }
            this.saletable1.setText(result);
            //System.out.println("Data printed");
        } catch (SQLException e) {
            this.infolabel.setText("SQLException found in the code");
        }
    }

    @FXML
    public void printClientTable(ActionEvent event1)throws  Exception{

        try {
            String result="";
            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT * FROM clients";
            ResultSet rs = myStmt.executeQuery(sql);

            while (rs.next()) {
                //  result=result+String.format("Sale id: "+rs.getString("saleId") + " Id of the sold product: " + rs.getString("idProduct") + "Sold by:"+rs.getString("soldBy")+ "Quantity sold: "+rs.getString("quantitySold")+" Date sold: "+rs.getString("dateSold")+" Total price from the sale: "+rs.getString("totalPrice"));
                result=result+String.format("Client id: "+rs.getInt("id") + " Name of client: " + rs.getString("name")+" Added by user: "+rs.getInt("addedByUsersId")+ " Email: "+rs.getString("email"));
                result=result+"\n";
            }
            this.clienttable1.setText(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addUpdateDeleteElement(String sqlText) throws Exception{
        try {
            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            myStmt.executeUpdate(sqlText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void makeSale(ActionEvent event) throws Exception{
        try{
        int counter = 0;
        if(checkClient(Integer.parseInt(this.idclientsale.getText()),Integer.parseInt(this.userID.getText())))
            counter+=1;
        else
            this.infolabel.setText("This client is not in your list. You need to add it first.(Check if you first entered your id)");
        if(checkProductAndAvailableQuantity(Integer.parseInt(this.idproduct.getText()),Integer.parseInt(this.quantitysold.getText())))
            counter+=1;
        else
            this.infolabel.setText("The product does not exists or there is not enough quantity");

        if(counter==2){
            float totalPrice = Integer.parseInt(this.quantitysold.getText()) * getPrice("products", Integer.parseInt(this.idproduct.getText()));
            String sqlText = String.format("INSERT INTO sales values(%d,%d,%d,%d,'%s',%s,%d)", Integer.parseInt(this.idsale.getText()), Integer.parseInt(this.idproduct.getText()), Integer.parseInt(this.userID.getText()), Integer.parseInt(this.quantitysold.getText()), this.datesold.getText(), totalPrice, Integer.parseInt(this.idclientsale.getText()));
            addUpdateDeleteElement(sqlText);
            this.infolabel.setText("Sale made successfully");
        }
    } catch (NumberFormatException e){
        this.infolabel.setText("Invalid input or you did not entered your id on the top of the page");
    }
    }

    public  boolean checkClient(int idClient, int usersId) throws SQLException {
        String result="";
        try {
            int counter = 0;
            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            String sql = String.format("SELECT * FROM clients WHERE id=%d",idClient);

            ResultSet rs = myStmt.executeQuery(sql);
            while (rs.next()) {
                result = rs.getString("addedByUsersId");
                if(Integer.parseInt(result)==usersId)
                    counter +=1;
            }
            if(counter>0)
                return true;
            else
                return false;
        } catch (SQLException e) {
            this.infolabel.setText("SQLException found in the code");
        }
        return false;
    }

    public  boolean checkProductAndAvailableQuantity(int idProduct, int neededQuantity) throws SQLException{
        String result="";
        try {

            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            String sql = String.format("SELECT * FROM products WHERE id=%d",idProduct);

            ResultSet rs = myStmt.executeQuery(sql);
            while (rs.next()) {
                result = rs.getString("quantity");
                if(Integer.parseInt(result)>=neededQuantity)
                    return true;
            }
            return false;
        } catch (SQLException e) {
            this.infolabel.setText("SQLException found in the code");
        }
        return false;
    }

        public float getPrice(String table, int id) throws SQLException{
        float result=0;
        try {

            Connection myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE id=%d",table,id);

            ResultSet rs = myStmt.executeQuery(sql);
            while (rs.next()) {
                result = rs.getFloat("pricePerItem");
            }
            return result;
        } catch (SQLException e) {
            this.infolabel.setText("SQLException found in the code");
        }
        return result;
    }

}
