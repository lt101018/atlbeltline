package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.DailyDetailRow30;

public class DailyDetail30 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;


    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("staffNames"));
        col3.setCellValueFactory(new PropertyValueFactory<>("visits"));
        col4.setCellValueFactory(new PropertyValueFactory<>("revenue"));
    }

    public void addElement(String eventName, String staffNames, int visits, int revenue) {
        DailyDetailRow30 row = new DailyDetailRow30(eventName, staffNames, visits,revenue);
        table.getItems().add(row);
    }


    public void btnBack(ActionEvent actionEvent) {
    }
}
