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
               // this.loginStatus.setText("Everything is ok");
            }else {
                this.loginStatus.setText("Wrong username or password");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void administratorLogin(){
        try {
            Stage adminStage = new Stage();
//            FXMLLoader loader = new FXMLLoader();
//            Pane root = (Pane)loader.load(getClass().getResource("/administrators/administratorFXML.fxml").openStream());
           Parent root = (Parent) FXMLLoader.load(getClass().getResource("admin.fxml"));

        //    AdministratorContorller administratorContorller = (AdministratorContorller)loader.getController();
            Scene scene = new Scene(root);
            adminStage.setScene(scene);
            adminStage.setTitle("Administrator Dashboard");
            adminStage.setResizable(false);
            adminStage.show();
        }catch (IOException e){
           e.printStackTrace();
        }
    }

    public void representativeLogin(){
        try {
              Stage reprStage = new Stage();
//            FXMLLoader loader = new FXMLLoader();
//            Pane root = (Pane)loader.load(getClass().getResource("/representatives/representativeFXML.fxml").openStream());
            System.out.println("here");
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("representative.fxml"));

//            RepresentativeController representativeController=(RepresentativeController)loader.getController();
            Scene scene = new Scene(root);
            reprStage.setScene(scene);
            reprStage.setTitle("Representative Dashboard");
            reprStage.setResizable(false);
            reprStage.show();
        }catch (IOException e){
            System.out.println("Not found");
            e.printStackTrace();
        }
    }
}
