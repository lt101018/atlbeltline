package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageSiteRow19;
import pojo.ManageTransitRow22;
import tools.MyAlert;

import java.io.IOException;

public class ManageTransit22 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TextField tfroute;
    public TextField tfprice1;
    public TextField tfprice2;
    public static String lastFxml;
    public ComboBox cbtransport;
    public ComboBox cbsite;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }


    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("route"));
        col2.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        col3.setCellValueFactory(new PropertyValueFactory<>("price"));
        col4.setCellValueFactory(new PropertyValueFactory<>("numConnectedSites"));
        col5.setCellValueFactory(new PropertyValueFactory<>("numTransitLogged"));
    }

    public void addElement(String route, String transportType, double price, int numConnectedSites, int numTransitLogged) {
        ManageTransitRow22 row = new ManageTransitRow22(route, transportType, price,numConnectedSites,numTransitLogged);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnEdit(ActionEvent actionEvent) throws IOException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a transit.");
            return;
        }
        ManageTransitRow22 selectedItem = (ManageTransitRow22)table.getSelectionModel().getSelectedItem();
        ///following jobs


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edittransit23.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        EditTransit23 controller = fxmlLoader.getController();
        controller.setLastFxml("managetransit22.fxml");
        controller.setTransportType(selectedItem.getTransportType());
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnDelete(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a transit.");
            return;
        }
        ManageTransitRow22 selectedItem = (ManageTransitRow22)table.getSelectionModel().getSelectedItem();
        ///following jobs

    }

    public void btnCreate(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createtransit24.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        CreateTransit24 controller = fxmlLoader.getController();
        controller.setLastFxml("managetransit22.fxml");
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
