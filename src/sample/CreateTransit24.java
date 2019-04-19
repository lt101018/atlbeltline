package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateTransit24 {
    public ListView listView;
    public Label labelRoute;
    public Label labelPrice;
    public static String lastFxml;
    public ComboBox cbTransportType;

    public void initialize(){

        cbTransportType.getItems().addAll(
                "ALL",
                "MARTA",
                "Bus",
                "Bike",
                "Other"
        );
    }

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnCreate(ActionEvent actionEvent) {
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)listView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
