package sample;

import connection.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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

public class EditTransit23 {
    public ListView listView;
    public Label labelTransportType;
    public static String lastFxml;
    private static Connection conn;
    public TextField tfRoute;
    public TextField tfPrice;
    public String prevType;
    public String prevRoute;
    ObservableList<String> list;
    public void initialize() {
        conn = ConnectionManager.getConn();
        list = FXCollections.observableArrayList();
        listView.setItems(list);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void initialize1() throws SQLException {

        labelTransportType.setText(prevType);
        tfRoute.setText(prevRoute);

        String sql = "select name as connected_sites\n" +
                "from connect\n" +
                "where type = '"+prevType+"' and route = '"+prevRoute+"';\n";
        System.out.println(sql);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            list.add(resultSet.getString(1));
        }
        listView.getSelectionModel().selectAll();


        sql = "select name from site as s where s.name not in ( select name as connected_sites\n" +
                "from connect\n" +
                "where type = '"+prevType+"' and route = '"+prevRoute+"');";
        System.out.println(sql);
        ResultSet resultSet1 = statement.executeQuery(sql);

        while(resultSet1.next()){
            list.add(resultSet1.getString(1));
        }

        statement.close();
    }

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)listView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnUpdate(ActionEvent actionEvent) throws SQLException {
        String sql = "update transit\n" +
                "set route = '"+tfRoute.getText()+"', price = "+tfPrice.getText()+"\n" +
                "where type = '"+prevType+"' and route = '"+prevRoute+"';";
        prevRoute = tfRoute.getText();
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);


        sql = "delete from connect\n" +
                "where type = '"+prevType+"' and route = '"+prevRoute+"';";
        statement.executeUpdate(sql);


        ObservableList<String> selectedSites = listView.getSelectionModel().getSelectedItems();
        for(String site:selectedSites) {
            sql = "insert into connect(name, type, route) values('"+site+"','"+prevType+"','"+prevRoute+"');";
            statement.executeUpdate(sql);
        }

        MyAlert.showAlert("Transit updated.");
        statement.close();
    }
}
