package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.VisitorHistoryRow38;

public class VisitorHistory38 {
    public MenuButton menuSite;
    public TextField tfEvent;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public DatePicker datepicker1;
    public DatePicker datepicker2;

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("date"));
        col2.setCellValueFactory(new PropertyValueFactory<>("event"));
        col3.setCellValueFactory(new PropertyValueFactory<>("site"));
        col4.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void addElement(String date, String event, String site, int price) {
        VisitorHistoryRow38 row = new VisitorHistoryRow38(date, event,site,price);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) {
    }

    public void btnFilter(ActionEvent actionEvent) {
    }
}
