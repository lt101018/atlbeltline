package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffEventDetail32 {
    public Label labelSite;
    public Label labelEvent;
    public Label labelStartDate;
    public Label labelEndDate;
    public Label labelPrice;
    public Label labelCapacity;
    public Label description;
    public Label labelStaffAssigned;
    public Label labelDurationDays;
    public static String lastFxml;

    public void setLabels(String site, String event, String startDate, String endDate) {
        labelSite.setText(site);
        labelEvent.setText(event);
        labelStartDate.setText(startDate);
        labelEndDate.setText(endDate);
    }

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)labelSite.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
