package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.TransitDetailRow36;

public class TransitDetail36 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public DatePicker datepicker;
    public MenuButton menuTransportType;
    public Label siteName;

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


    public void btnBack(ActionEvent actionEvent) {
    }

    public void btnLogTransit(ActionEvent actionEvent) {
    }
}
