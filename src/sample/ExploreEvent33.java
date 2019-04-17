package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ExploreEventRow33;
import tools.MyAlert;

import java.io.IOException;

public class ExploreEvent33 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TableColumn col6;
    public TextField tfKeyword;
    public TextField tfPriceRange1;
    public TextField tfPriceRange2;
    public TextField tfTotalVisits1;
    public TextField tfTotalVisits2;
    public TextField tfname;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public MenuButton menuSiteName;
    public CheckBox checkVisited;
    public CheckBox checkSoldOut;

    public String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        col3.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        col4.setCellValueFactory(new PropertyValueFactory<>("ticketRemaining"));
        col5.setCellValueFactory(new PropertyValueFactory<>("totalVisits"));
        col6.setCellValueFactory(new PropertyValueFactory<>("myVisits"));
    }

    public void addElement(String eventName, String siteName, int ticketPrice, int ticketRemaining, int totalVisits, int myVisits) {
        ExploreEventRow33 row = new ExploreEventRow33(eventName, siteName, ticketPrice,ticketRemaining,totalVisits,myVisits);
        table.getItems().add(row);
    }


    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnEventDetail(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select an event.");
            return;
        }
        ExploreEventRow33 selectedItem = (ExploreEventRow33)table.getSelectionModel().getSelectedItem();
        ///following jobs
    }
}
