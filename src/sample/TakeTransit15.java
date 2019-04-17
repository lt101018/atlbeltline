package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.TakeTransitRow15;

import java.io.IOException;

public class TakeTransit15 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public Button filter;
    public DatePicker datepicker;
    public MenuButton menusite;
    public MenuButton menutransport;
    public TextField price1;
    public TextField price2;
    public String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("route"));
        col2.setCellValueFactory(new PropertyValueFactory<>("type"));
        col3.setCellValueFactory(new PropertyValueFactory<>("price"));
        col4.setCellValueFactory(new PropertyValueFactory<>("numSite"));
    }


    public void addElement(String route, String type, double price, int numSite) {
        TakeTransitRow15 row = new TakeTransitRow15("newRout", "newType", 2.5, 5);
        table.getItems().add(row);
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)filter.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnLogTransit(ActionEvent actionEvent) {
    }

// this is how to change date format
/*
String mrgDate3 = mrgRqstDt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
System.out.println("Date of Merge: " + mrgDate3);
*/

}




