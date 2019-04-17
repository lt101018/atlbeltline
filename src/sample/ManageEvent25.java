package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageEventRow25;
import pojo.ManageTransitRow22;
import tools.MyAlert;

import java.io.IOException;

public class ManageEvent25 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TextField tfKeyword;
    public TextField tfRevenue1;
    public TextField tfRevenue2;
    public TextField tfduration1;
    public TextField tfduration2;
    public TextField tfTotalVisits1;
    public TextField tfTotalVisits2;
    public TextField tfname;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("staffCount"));
        col3.setCellValueFactory(new PropertyValueFactory<>("duration"));
        col4.setCellValueFactory(new PropertyValueFactory<>("totalVisits"));
        col5.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
    }

    public void addElement(String name, int staffCount, int duration, int totalVisits, int totalRevenue) {
        ManageEventRow25 row = new ManageEventRow25(name, staffCount, duration,totalVisits,totalRevenue);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnEdit(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select an event.");
            return;
        }
        ManageEventRow25 selectedItem = (ManageEventRow25)table.getSelectionModel().getSelectedItem();
        ///following jobs
    }

    public void btnDelete(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select an event.");
            return;
        }
        ManageEventRow25 selectedItem = (ManageEventRow25)table.getSelectionModel().getSelectedItem();
        ///following jobs
    }

    public void btnCreate(ActionEvent actionEvent) {
    }
}
