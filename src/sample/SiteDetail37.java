package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pojo.UserInfo;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class SiteDetail37 {
    public DatePicker datepicker;
    public Label siteName;
    public Label openEveryday;
    public Label address;
    public static String lastFxml;
    private static Connection conn;

    public void setSite(String site) throws SQLException, IOException {
        siteName.setText(site);
        setAddressAndOpenEveryday(site);
    }

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize() throws IOException, SQLException {
        conn = ConnectionManager.getConn();
    }

    public void setAddressAndOpenEveryday(String sitename) throws IOException, SQLException {
        Statement statement = conn.createStatement();
        String sqlForSite = "select name, address, openeveryday from site where name=" + "'"+ sitename + "'";
        ResultSet resultSet = statement.executeQuery(sqlForSite);
        while(resultSet.next()){
            address.setText(resultSet.getString(2));
            openEveryday.setText(resultSet.getString(3));
        }
        statement.close();
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)siteName.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnLogVisit(ActionEvent actionEvent) throws IOException, SQLException {
        Statement statement = conn.createStatement();
        String formattedDate = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String siteValue = siteName.getText();
        String openEverydayValue = openEveryday.getText();
        String addressValue = address.getText();
        String sqlForLog = "";

        String sqlForCheck = "select *\n" +
                "from visit_site\n" +
                "where visitorusername='"+ UserInfo.username +"' and name='" + siteValue + "' and  visitdate='" + formattedDate + "'";

        ResultSet resultSet = statement.executeQuery(sqlForCheck);
        while(resultSet.next()){
            MyAlert.showAlert("You have visited here!");
            return;
        }

        sqlForLog = "insert into visite_site(visitorusername,name,visitdate) values('"+ UserInfo.username +"','" +
                siteValue + "','"+ formattedDate +"')";

        statement.executeUpdate(sqlForLog);
        statement.close();
    }
}
