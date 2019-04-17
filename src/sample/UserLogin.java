package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UserLogin {
    public TextField tfemail;
    public TextField tfpassword;

    public void login(ActionEvent actionEvent) throws IOException {
        String nextFxml = tfemail.getText();
        Parent root = FXMLLoader.load(getClass().getResource(nextFxml+".fxml"));
        Stage stage = (Stage)tfemail.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void register(ActionEvent actionEvent) {
    }

}
