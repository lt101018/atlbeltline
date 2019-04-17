package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageStaffRow28;

import java.io.IOException;

public class ManageStaff28 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TextField tfLastName;
    public TextField tfFirstName;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public MenuButton menuSite;
    public String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("eventShift"));
    }

    public void addElement(String staffName, int eventShift) {
        ManageStaffRow28 row = new ManageStaffRow28(staffName, eventShift);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) {
    }
}
