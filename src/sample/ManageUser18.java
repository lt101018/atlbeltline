package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.ManageUserRow18;

public class ManageUser18 {
    public MenuButton menuusertype;
    public MenuButton menustatus;
    public TextField tfusername;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;

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

    public void btnBack(ActionEvent actionEvent) {
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnApprove(ActionEvent actionEvent) {
    }

    public void btnDecline(ActionEvent actionEvent) {
    }
}
