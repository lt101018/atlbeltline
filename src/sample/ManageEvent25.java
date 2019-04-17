package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.ManageEventRow25;

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
