package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageSiteRow19;
import pojo.ManageTransitRow22;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManageTransit22 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TextField tfroute;
    public TextField tfprice1;
    public TextField tfprice2;
    public static String lastFxml;
    public ComboBox cbtransport;
    public ComboBox cbsite;
    private static Connection conn;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }


    public void initialize() throws SQLException {
        col1.setCellValueFactory(new PropertyValueFactory<>("route"));
        col2.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        col3.setCellValueFactory(new PropertyValueFactory<>("price"));
        col4.setCellValueFactory(new PropertyValueFactory<>("numConnectedSites"));
        col5.setCellValueFactory(new PropertyValueFactory<>("numTransitLogged"));
        conn = ConnectionManager.getConn();
        cbtransport.getItems().addAll(
                "ALL",
                "MARTA",
                "Bus",
                "Bike",
                "Other"
        );


        String sql = "select name from site";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        cbsite.getItems().add("ALL");
        while(resultSet.next()){
            cbsite.getItems().add(resultSet.getString("name"));
        }
        cbsite.getItems().add("Other");

        cbsite.getSelectionModel().select(0);
        cbtransport.getSelectionModel().select(0);
        statement.close();
    }

    public void addElement(String route, String transportType, double price, int numConnectedSites, int numTransitLogged) {
        ManageTransitRow22 row = new ManageTransitRow22(route, transportType, price,numConnectedSites,numTransitLogged);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) throws SQLException {
        table.getItems().clear();

        String typeSql1 = "";
        if(!cbtransport.getValue().toString().equals("ALL"))
            typeSql1 = "and c.type ='"+cbtransport.getValue().toString()+"'";

        String routeSql1 = "";
        if(!(tfroute.getText().length()==0))
            routeSql1 = "and c.route='"+tfroute.getText()+"'";

        String typeSql2 = "";
        if(!cbtransport.getValue().toString().equals("ALL"))
            typeSql2 = "and ta.type ='"+cbtransport.getValue().toString()+"'";

        String routeSql2 = "";
        if(!(tfroute.getText().length()==0))
            routeSql2 = "and ta.route='"+tfroute.getText()+"'";

        String siteSql = "";
        if(!cbsite.getValue().toString().equals("ALL"))
            siteSql = "where name='"+cbsite.getValue().toString()+"'";


        String priceL = "0", priceR = Integer.MAX_VALUE+"";
        if(tfprice1.getText().length()!=0) priceL = tfprice1.getText();
        if(tfprice2.getText().length()!=0) priceR = tfprice2.getText();
        String priceSql = "and t.price between "+priceL+" and "+priceR;

        String sql1 = "select t.route, t.type, t.price, count(*) as connected_sites\n" +
                "from transit as t, connect as c\n" +
                "where c.type=t.type and c.route=t.route "+typeSql1+" "+routeSql1+" and t.route in (select route from connect "+siteSql+") "+priceSql+" \n" +
                "group by c.route, c.type;\n";

        String sql2 = "select count(*) as transit_logged\n" +
                "from take as ta, transit as t\n" +
                "where ta.type=t.type and ta.route=t.route "+typeSql2+" "+routeSql2+" and t.route in (select route from connect "+siteSql+") "+priceSql+" \n" +
                "group by ta.type, ta.route;";
        System.out.println(sql1);
        System.out.println(sql2);
        Statement statement1 = conn.createStatement();
        Statement statement2 = conn.createStatement();
        ResultSet resultSet1 = statement1.executeQuery(sql1);
        ResultSet resultSet2 = statement2.executeQuery(sql2);
        while(resultSet1.next() && resultSet2.next()){
            addElement(resultSet1.getString(1),resultSet1.getString(2),resultSet1.getDouble(3), resultSet1.getInt(4), resultSet2.getInt(1));
        }
        statement1.close();
        statement2.close();
    }

    public void btnEdit(ActionEvent actionEvent) throws IOException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a transit.");
            return;
        }
        ManageTransitRow22 selectedItem = (ManageTransitRow22)table.getSelectionModel().getSelectedItem();
        ///following jobs

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edittransit23.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        EditTransit23 controller = fxmlLoader.getController();
        controller.setLastFxml("managetransit22.fxml");
        controller.setTransportType(selectedItem.getTransportType());
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnDelete(ActionEvent actionEvent) throws SQLException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a transit.");
            return;
        }
        ManageTransitRow22 selectedItem = (ManageTransitRow22)table.getSelectionModel().getSelectedItem();
        ///following jobs
        String sql = "delete from take \n" +
                "where route = '"+selectedItem.getRoute()+"';";
        System.out.println(sql);
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);

        MyAlert.showAlert("Transit deleted");
        btnFilter(null);

        statement.close();

    }

    public void btnCreate(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createtransit24.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        CreateTransit24 controller = fxmlLoader.getController();
        controller.setLastFxml("managetransit22.fxml");
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
