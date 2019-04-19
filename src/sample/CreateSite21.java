package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateSite21 {
    public TextField tfName;
    public TextField tfAddress;
    public TextField tfZipcode;
    public CheckBox cbOpenEveryday;
    public static String lastFxml;
    public ComboBox cbManager;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)tfName.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnCreate(ActionEvent actionEvent) {
    }
}
