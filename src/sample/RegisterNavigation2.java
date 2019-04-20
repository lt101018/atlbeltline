package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterNavigation2 {

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField pwTextField;

    @FXML
    public Button userOnlyBttn;

    @FXML
    public Button visitorOnlyBttn;

    @FXML
    public Button employeeOnlyBttn;

    @FXML
    public Button employeeVisitorBttn;

    @FXML
    public Button backBttn;

    public static String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registeruseronly3.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        RegisterUserOnly3 controller = fxmlLoader.getController();
        controller.setLastFxml("registernavigation2.fxml");

        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("registervisitoronly4.fxml"));
        root = (Parent)fxmlLoader2.load();
        RegisterVisitorOnly4 controller2 = fxmlLoader2.getController();
        controller2.setLastFxml("registernavigation2.fxml");
    }

    public void userOnlyRegister(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registeruseronly3.fxml"));
        Stage stage = (Stage)userOnlyBttn.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));
    }

    public void visitorOnlyRegister(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registervisitoronly4.fxml"));
        Stage stage = (Stage)visitorOnlyBttn.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));
    }

    public void employeeOnlyRegister(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registeremployeeonly5.fxml"));
        Stage stage = (Stage)userOnlyBttn.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));

    }

    public void employeeVisitorRegister(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registeremployeevisitor.fxml"));
        Stage stage = (Stage)userOnlyBttn.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));

    }

    public void backFun(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("userlogin.fxml"));
        Stage stage = (Stage)userOnlyBttn.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));
    }
}
