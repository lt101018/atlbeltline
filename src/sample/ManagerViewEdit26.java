package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerViewEdit26 {



    //This part is for fxml component
    public Label nameValue;
    public ListView listView;
    public Label startDateValue;
    public Label minStaffValueminStaffValue;
    public Label priceValue;
    public Label endDateValue;
    public Label capacityValue;
    public TextArea descrip;
    public TextField tfTotalVisits1;
    public TextField tfTotalVisits2;
    public TextField tfRevenue1;
    public TextField tfRevenue2;
    

    //This part is for variables
    public static String lastFxml;
    private String eventname;
    private String price;
    private String startdate;
    private String enddate;
    private String minreqstaff;
    private String cap;

    public void setRow() {

    }

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)listView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) {

    }

    public void btnUpdate(ActionEvent actionEvent) {
    }
}
