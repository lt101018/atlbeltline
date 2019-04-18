package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.TransitHistroyRow16;

import java.io.IOException;

public class TransitHistory16 {
    public MenuButton menutransport;
    public MenuButton menusite;
    public TextField tfroute;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public static String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("date"));
        col2.setCellValueFactory(new PropertyValueFactory<>("route"));
        col3.setCellValueFactory(new PropertyValueFactory<>("transport"));
        col4.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void addElement(String date, String route, String transport, double price) {
        TransitHistroyRow16 row = new TransitHistroyRow16(date, route, transport, price);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)menutransport.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) {
    }
}
