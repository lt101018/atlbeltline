package sample;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.TakeTransitRow;

public class TakeTransit {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public Button filter;

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("route"));
        col2.setCellValueFactory(new PropertyValueFactory<>("type"));
        col3.setCellValueFactory(new PropertyValueFactory<>("price"));
        col4.setCellValueFactory(new PropertyValueFactory<>("numSite"));
    }


    public void addElement(String route, String type, double price, int numSite) {
        TakeTransitRow row = new TakeTransitRow("newRout", "newType", 2.5, 5);
        table.getItems().add(row);
    }

}




