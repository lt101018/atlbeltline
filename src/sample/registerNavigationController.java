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

public class registerNavigationController {

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

    public void userOnlyRegister(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registeremployee.fxml"));
        Stage stage = (Stage)userOnlyBttn.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));
    }

    public void visitorOnlyRegister(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newScreen.fxml"));
        Stage stage = (Stage)visitorOnlyBttn.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));
    }

}
