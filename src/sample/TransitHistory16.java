package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.TransitHistroyRow16;
import pojo.UserInfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class TransitHistory16 {
    public TextField tfroute;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public static String lastFxml;
    public ComboBox cbSite;
    public ComboBox cbTransportType;
    private static Connection conn;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize() throws SQLException {
        col1.setCellValueFactory(new PropertyValueFactory<>("date"));
        col2.setCellValueFactory(new PropertyValueFactory<>("route"));
        col3.setCellValueFactory(new PropertyValueFactory<>("transport"));
        col4.setCellValueFactory(new PropertyValueFactory<>("price"));
        cbTransportType.getItems().addAll(
                "ALL",
                "MARTA",
                "Bus",
                "Bike",
                "Other"
        );

        conn = ConnectionManager.getConn();
        String sql = "select name from site";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        cbSite.getItems().add("ALL");
        while(resultSet.next()){
            cbSite.getItems().add(resultSet.getString("name"));
        }
        cbSite.getItems().add("Other");
        statement.close();

        cbSite.getSelectionModel().select(0);
        cbTransportType.getSelectionModel().select(0);
    }

    public void addElement(String date, String route, String transport, double price) {
        TransitHistroyRow16 row = new TransitHistroyRow16(date, route, transport, price);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)datepicker1.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) throws SQLException {
        table.getItems().clear();

        String siteSql = "";
        if(!cbSite.getValue().toString().equals("ALL"))
            siteSql = "and c.name = '"+cbSite.getValue().toString()+"'";

        String transportSql = "";
        String cbContent = cbTransportType.getValue().toString();
        if(cbContent.equals("MARTA") || cbContent.equals("Bus") || cbContent.equals("Bike"))
            transportSql = "and ta.type='"+cbContent+"' and t.type='"+cbContent+"'";

        String routeSql = "";
        if(tfroute.getText().length()!=0)
            routeSql = "and ta.route='"+tfroute.getText()+"' and t.route='"+tfroute.getText()+"'";

        String dateSql = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            String formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateSql = "and ta.takedate between '"+formattedDate1+"' and '"+formattedDate2+"'";
        }

        String sql = "select distinct ta.takedate, ta.route, ta.type, t.price\n" +
                "from take as ta, transit as t, connect as c\n" +
                "where ta.type = t.type and ta.route = t.route and c.type = t.type and c.route = t.route " +
                "and ta.username='"+UserInfo.username+"' "+transportSql+" "+siteSql+" "+routeSql+" "+dateSql+"\n" +
                "order by ta.takedate;";

        System.out.println(sql);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            addElement(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4));
        }
        statement.close();
    }
}
