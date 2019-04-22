package sample;

import connection.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTransit24 {
    public ListView listView;
    public static String lastFxml;
    public ComboBox cbTransportType;
    public TextField tfRoute;
    public TextField tfPrice;
    private static Connection conn;

    ObservableList<String> list;

    public void initialize()  {

        list = FXCollections.observableArrayList();
        listView.setItems(list);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        conn = ConnectionManager.getConn();
        cbTransportType.getItems().addAll(
                "ALL",
                "MARTA",
                "Bus",
                "Bike"
        );

        String sql = "select name from site";
        try {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            list.add(resultSet.getString(1));
        }
        statement.close();
    }catch (SQLException e){
        MyAlert.showAlert(e.getMessage());
    }
    }

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnCreate(ActionEvent actionEvent)  {
        String sql = "insert into transit( type, route, price) values('"+cbTransportType.getValue().toString() +"','"+tfRoute.getText()+"',"+tfPrice.getText()+");";
        System.out.println(sql);
        try {
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);

        ObservableList<String> selectedSites = listView.getSelectionModel().getSelectedItems();
        for(String site:selectedSites) {
            sql = "insert into connect(name, type, route) values('"+site+"','"+cbTransportType.getValue().toString()+"','"+tfRoute.getText()+"');";
            statement.executeUpdate(sql);
        }

        sql = "insert into take(username, type, route, takedate) values(null, '"+cbTransportType.getValue().toString()+"','"+tfRoute.getText()+"', null);";
        statement.executeUpdate(sql);

        MyAlert.showAlert("Transit created.");
        statement.close();
    }catch (SQLException e){
        MyAlert.showAlert(e.getMessage());
    }
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)listView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
