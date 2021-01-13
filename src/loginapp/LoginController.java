package loginapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbstatus;
    @FXML
    public TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatus;


    public LoginController() throws Exception {
    }


    public void initialize(URL url, ResourceBundle rs){
        if(this.loginModel.isDatabaseConnected()){
            this.dbstatus.setText("Connected to Database");
        }else {
            this.dbstatus.setText("Not connected to Database");
        }
    }

    @FXML
    public void Login(ActionEvent event){
        try {
            if(this.loginModel.isLogin(this.username.getText(),this.password.getText())){
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                if(loginModel.getRole(this.username.getText()).equals("administrator")){
                    administratorLogin();
                }else if(loginModel.getRole(this.username.getText()).equals("representative")){
                    representativeLogin();
                }
            }else {
                this.loginStatus.setText("Wrong username or password");
            }
        }catch (SQLException e) {
            this.loginStatus.setText("SQL Exception found in the code");
        }

    }


    public void administratorLogin(){
        try {
            Stage adminStage = new Stage();
           Parent root = (Parent) FXMLLoader.load(getClass().getResource("admin.fxml"));

            Scene scene = new Scene(root);
            adminStage.setScene(scene);
            adminStage.setTitle("Administrator Dashboard");
            adminStage.setResizable(false);
            adminStage.show();
        }catch (IOException e){
            System.out.println("Exception with load method");
        }catch (NullPointerException e){
            System.out.println("FXML file does not exist");
        }
    }

    public void representativeLogin(){
        try {
              Stage reprStage = new Stage();

            Parent root = (Parent) FXMLLoader.load(getClass().getResource("representative.fxml"));

            Scene scene = new Scene(root);
            reprStage.setScene(scene);
            reprStage.setTitle("Representative Dashboard");
            reprStage.setResizable(false);
            reprStage.show();
        }catch (IOException e){
            System.out.println("Exception with load method");
        }catch (NullPointerException e){
            this.loginStatus.setText("FXML file does not exist");
        }
    }
}
