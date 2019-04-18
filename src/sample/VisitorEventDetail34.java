package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class VisitorEventDetail34 {
    public DatePicker datepicker;
    public Label labelSite;
    public Label labelEvent;
    public Label labelStartDate;
    public Label labelEndDate;
    public Label labelTicketPrice;
    public Label labelTicketRemaining;
    public Label description;
    public static String lastFxml;

    public void setLabels(String eventName, String siteName, int ticketPrice, int ticketRemaining) {
        labelEvent.setText(eventName);
        labelSite.setText(siteName);
        labelTicketPrice.setText(ticketPrice+"");
        labelTicketRemaining.setText(ticketRemaining+"");
    }
    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)labelSite.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnLogVisit(ActionEvent actionEvent) {

    }
}
