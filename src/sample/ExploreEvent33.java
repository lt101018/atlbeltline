package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.ExploreEventRow33;

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


    public void btnBack(ActionEvent actionEvent) {
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnEventDetail(ActionEvent actionEvent) {
    }
}
