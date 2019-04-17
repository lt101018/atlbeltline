package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.StaffViewScheduleRow31;

public class StaffViewSchedule31 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TextField tfDescriptionKeyword;
    public TextField tfEventName;
    public DatePicker datepicker1;
    public DatePicker datepicker2;

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        col3.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        col4.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        col5.setCellValueFactory(new PropertyValueFactory<>("staffCount"));
    }

    public void addElement(String eventName, String siteName, String startDate, String endDate, int staffCount) {
        StaffViewScheduleRow31 row = new StaffViewScheduleRow31(eventName, siteName, startDate,endDate,staffCount);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) {
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnViewEvent(ActionEvent actionEvent) {
    }
}
