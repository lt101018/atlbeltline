package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.TransitDetailRow36;
import pojo.UserInfo;
import pojo.VisitorHistoryRow38;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class VisitorHistory38 {
    public TextField tfEvent;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public static String lastFxml;
    public ComboBox cbSite;
    private static Connection conn;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize()throws SQLException, IOException {
        conn = ConnectionManager.getConn();
        col1.setCellValueFactory(new PropertyValueFactory<>("date"));
        col2.setCellValueFactory(new PropertyValueFactory<>("event"));
        col3.setCellValueFactory(new PropertyValueFactory<>("site"));
        col4.setCellValueFactory(new PropertyValueFactory<>("price"));

        Statement statement = conn.createStatement();
        String sqlForSite = "select name\n" +
                "from site";
        ResultSet resultSet = statement.executeQuery(sqlForSite);
        while(resultSet.next()){
            cbSite.getItems().add(resultSet.getString(1));
        }
        statement.close();
    }

    public void addElement(String date, String event, String site, int price) {
        VisitorHistoryRow38 row = new VisitorHistoryRow38(date, event,site,price);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) throws SQLException {
        String username = UserInfo.username;
        String formattedStartDate = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedEndDate = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sitename = cbSite.getValue().toString();
        String eventname = tfEvent.getText();

        Statement statement = conn.createStatement();

        String sqlForFilter = "select ve.visitdate, ve.eventname,ve.sitename, e.price"+
        "from visit_event as ve, event as e" +
        "where" +
        "ve.eventname=e.name" +
        "and ve.sitename=e.sitename" +
        "and ve.eventstartdate=e.startdate" +
        "and ve.visitorusername='"+ username +"'" +
        "and ve.eventname like '%" + eventname + "%'" +
        "and ve.sitename='"+ sitename +"'" +
        "and ve.visitdate>='"+ formattedStartDate +"' and ve.visitdate<='" + formattedEndDate + "'" +
        "union" +
        "select visitdate, null, name, 0" +
        "from visit_site" +
        "where visitorusername='"+ username +"'" +
        "and name='"+ sitename +"'" +
        "and visitdate>='"+ formattedStartDate +"' and visitdate<='" + formattedEndDate + "'";

        ResultSet resultSet = statement.executeQuery(sqlForFilter);
        while(resultSet.next()){
            addElement(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),Integer.parseInt(resultSet.getString(4)));
        }
        statement.close();
    }
}
