package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SiteDetail37 {
    public DatePicker datepicker;
    public Label siteName;
    public Label openEveryday;
    public Label address;
    public String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)siteName.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnLogVisit(ActionEvent actionEvent) {
    }
}
