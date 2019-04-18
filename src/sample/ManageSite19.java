package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageSiteRow19;
import pojo.ManageUserRow18;
import tools.MyAlert;

import java.io.IOException;

public class ManageSite19 {
    public MenuButton menusite;
    public MenuButton menumanager;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public MenuButton menuopeneveryday;
    public String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("manager"));
        col3.setCellValueFactory(new PropertyValueFactory<>("openeveryday"));
    }

    public void addElement(String name, String manager, String openeveryday) {
        ManageSiteRow19 row = new ManageSiteRow19(name, manager, openeveryday);
        table.getItems().add(row);
    }


    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)menusite.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnEdit(ActionEvent actionEvent) throws IOException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a site.");
            return;
        }
        ManageSiteRow19 selectedItem = (ManageSiteRow19)table.getSelectionModel().getSelectedItem();
        ///following jobs

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editsite20.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        EditSite20 controller = fxmlLoader.getController();
        controller.setLastFxml("managesite19.fxml");
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnDelete(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a site.");
            return;
        }
        ManageSiteRow19 selectedItem = (ManageSiteRow19)table.getSelectionModel().getSelectedItem();
        ///following jobs



    }

    public void btnCreate(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createsite21.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        CreateSite21 controller = fxmlLoader.getController();
        controller.setLastFxml("managesite19.fxml");
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
