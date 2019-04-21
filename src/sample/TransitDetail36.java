package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.TransitDetailRow36;
import tools.MyAlert;

import java.io.IOException;

public class TransitDetail36 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public DatePicker datepicker;
    public Label siteName;
    public static String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void setSite(String name){
        siteName.setText(name);
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("route"));
        col2.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        col3.setCellValueFactory(new PropertyValueFactory<>("price"));
        col4.setCellValueFactory(new PropertyValueFactory<>("numConnectedSites"));
    }

    public void addElement(String route, String transportType, double price, int numConnectedSites) {
        TransitDetailRow36 row = new TransitDetailRow36(route, transportType,price,numConnectedSites);
        table.getItems().add(row);
    }


    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnLogTransit(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a transit.");
            return;
        }
        TransitDetailRow36 selectedItem = (TransitDetailRow36)table.getSelectionModel().getSelectedItem();
        ///following jobs
        
    }
}
