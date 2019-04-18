package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageUserRow18;
import pojo.TakeTransitRow15;
import tools.MyAlert;

import java.io.IOException;

public class ManageUser18 {
    public MenuButton menuusertype;
    public MenuButton menustatus;
    public TextField tfusername;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public static String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("username"));
        col2.setCellValueFactory(new PropertyValueFactory<>("emailCount"));
        col3.setCellValueFactory(new PropertyValueFactory<>("userType"));
        col4.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void addElement(String username, int emailCount, String userType, String status) {
        ManageUserRow18 row = new ManageUserRow18(username, emailCount, userType, status);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)menuusertype.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnApprove(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a user.");
            return;
        }
        ManageUserRow18 selectedItem = (ManageUserRow18)table.getSelectionModel().getSelectedItem();
        ///following jobs
    }

    public void btnDecline(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a user.");
            return;
        }
        ManageUserRow18 selectedItem = (ManageUserRow18)table.getSelectionModel().getSelectedItem();
        ///following jobs
    }
}