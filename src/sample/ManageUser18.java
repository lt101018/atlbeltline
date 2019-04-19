package sample;

import connection.ConnectionManager;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManageUser18 {
    public TextField tfusername;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public static String lastFxml;
    public ComboBox cbusertype;
    public ComboBox cbstatus;
    private static Connection conn;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("username"));
        col2.setCellValueFactory(new PropertyValueFactory<>("emailCount"));
        col3.setCellValueFactory(new PropertyValueFactory<>("userType"));
        col4.setCellValueFactory(new PropertyValueFactory<>("status"));
        conn = ConnectionManager.getConn();
        cbstatus.getItems().addAll(
                "ALL",
                "Approved",
                "Pending",
                "Declined",
                "Other"
        );
        cbusertype.getItems().addAll(
                "ALL",
                "User",
                "Visitor",
                "Staff",
                "Manager",
                "Other"
        );
        cbstatus.getSelectionModel().select(0);
        cbusertype.getSelectionModel().select(0);
    }

    public void addElement(String username, int emailCount, String userType, String status) {
        ManageUserRow18 row = new ManageUserRow18(username, emailCount, userType, status);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)tfusername.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) throws SQLException {
        table.getItems().clear();



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
