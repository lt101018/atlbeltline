package sample;

import connection.ConnectionManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LogInScreenController {

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField pwTextField;

    @FXML
    public Button loginBttn;

    @FXML
    public Button registerBttn;


    public void login(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
         Stage stage = (Stage)loginBttn.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));
    }

    public void Register(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registerNavigation.fxml"));
        Stage stage = (Stage)loginBttn.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));
    }
}
