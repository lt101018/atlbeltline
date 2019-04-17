package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.ManageTransitRow22;

public class ManageTransit22 {
    public MenuButton menutransport;
    public MenuButton menusite;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TextField tfroute;
    public TextField tfprice1;
    public TextField tfprice2;


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

    public void btnBack(ActionEvent actionEvent) {
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnEdit(ActionEvent actionEvent) {
    }

    public void btnDelete(ActionEvent actionEvent) {
    }

    public void btnCreate(ActionEvent actionEvent) {
    }
}
