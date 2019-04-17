package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.ExploreSiteRow35;

public class ExploreSite35 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TextField tfEventCountRange1;
    public TextField tfEventCountRange2;
    public TextField tfTotalVisits1;
    public TextField tfTotalVisits2;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public MenuButton menuName;
    public CheckBox checkVisited;
    public MenuButton menuOpenEveryday;

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        col3.setCellValueFactory(new PropertyValueFactory<>("totalVisits"));
        col4.setCellValueFactory(new PropertyValueFactory<>("myVisits"));
    }

    public void addElement(String siteName, int eventCount, int totalVisits, int myVisits) {
        ExploreSiteRow35 row = new ExploreSiteRow35(siteName, eventCount,totalVisits,myVisits);
        table.getItems().add(row);
    }


    public void btnBack(ActionEvent actionEvent) {
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnSiteDetail(ActionEvent actionEvent) {
    }

    public void btnTransitDetail(ActionEvent actionEvent) {
    }
}
