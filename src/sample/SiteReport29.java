package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.SiteReportRow29;
import tools.MyAlert;

import java.io.IOException;

public class SiteReport29 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TextField tfRevenue1;
    public TextField tfRevenue2;
    public TextField tfeventcount1;
    public TextField tfeventcount2;
    public TextField tfTotalVisits1;
    public TextField tfTotalVisits2;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public TextField tfStaffCount1;
    public TextField tfStaffCount2;
    public static String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("date"));
        col2.setCellValueFactory(new PropertyValueFactory<>("eventCount"));
        col3.setCellValueFactory(new PropertyValueFactory<>("staffCount"));
        col4.setCellValueFactory(new PropertyValueFactory<>("totalVisits"));
        col5.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
    }

    public void addElement(String date, int eventCount, int staffCount, int totalVisits, int totalRevenue) {
        SiteReportRow29 row = new SiteReportRow29(date, eventCount, staffCount,totalVisits,totalRevenue);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnDailyDetail(ActionEvent actionEvent) throws IOException {
//        if(table.getSelectionModel().getSelectedItem() == null) {
//            MyAlert.showAlert("You need to select a site.");
//            return;
//        }
        SiteReportRow29 selectedItem = (SiteReportRow29)table.getSelectionModel().getSelectedItem();
        ///following jobs

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dailydetail30.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        DailyDetail30 controller = fxmlLoader.getController();
        controller.setLastFxml("sitereport29.fxml");
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
