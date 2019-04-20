package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageStaffRow28;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class ManageStaff28 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TextField tfLastName;
    public TextField tfFirstName;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public static String lastFxml;
    public ComboBox cbSite;
    private static Connection conn;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize() throws SQLException {
        conn = ConnectionManager.getConn();
        col1.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("eventShift"));

        String sql = "select name from site";

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        cbSite.getItems().add("ALL");
        while(resultSet.next()){
            cbSite.getItems().add(resultSet.getString("name"));
        }

        cbSite.getSelectionModel().select(0);
        statement.close();
    }

    public void addElement(String staffName, int eventShift) {
        ManageStaffRow28 row = new ManageStaffRow28(staffName, eventShift);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) throws SQLException {

        table.getItems().clear();
        String fnameSql = "";
        if(tfFirstName.getText().length()!=0){
            fnameSql = "and u.firstname = '"+tfFirstName.getText()+"'";
        }

        String lnameSql = "";
        if(tfLastName.getText().length()!=0){
            lnameSql = "and u.lastname = '"+tfLastName.getText()+"'";
        }

        String siteSql = "";
        if(!cbSite.getValue().toString().equals("ALL")){
            siteSql = "and a.sitename = '"+cbSite.getValue().toString()+"'";
        }

        String dateSql = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            String formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateSql = "and a.startdate between '"+formattedDate1+"' and '"+formattedDate2+"'";
        }

        String sql = "select u.firstname, u.lastname\n" +
                "from user as u, employee as e, assign_to as a\n" +
                "where u.username = e.username and a.staffusername = e.username and e.employeetype = 'staff' "+fnameSql+" "+lnameSql+" "+siteSql+" "+dateSql+"\n" +
                "group by u.firstname, u.lastname;";

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            fnameSql = "and u.firstname = '"+resultSet.getString(1)+"'";
            lnameSql = "and u.lastname = '"+resultSet.getString(2)+"'";
            String sql1 = "select count(*)\n" +
                    "from user as u, employee as e, assign_to as a\n" +
                    "where u.username = e.username and a.staffusername = e.username and e.employeetype = 'staff' "+fnameSql+" "+lnameSql+" "+siteSql+" "+dateSql+";";
            Statement statement1 = conn.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);
            if(resultSet1.next())
                addElement(resultSet.getString(1)+" "+resultSet.getString(2), resultSet1.getInt(1));
            statement1.close();
        }

        statement.close();
    }
}
