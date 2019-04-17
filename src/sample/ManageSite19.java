package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.ManageSiteRow19;

public class ManageSite19 {
    public MenuButton menusite;
    public MenuButton menumanager;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public MenuButton menuopeneveryday;

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("manager"));
        col3.setCellValueFactory(new PropertyValueFactory<>("openeveryday"));
    }

    public void addElement(String name, String manager, String openeveryday) {
        ManageSiteRow19 row = new ManageSiteRow19(name, manager, openeveryday);
        table.getItems().add(row);
    }


    public void btnBack(ActionEvent actionEvent) {
    }

    public void btnFilter(ActionEvent actionEvent) {
    }

    public void btnEdit(ActionEvent actionEvent) {
    }

    public void btnDelete(ActionEvent actionEvent) {
    }

    public void btnCreate(ActionEvent actionEvent) {
    }
}
