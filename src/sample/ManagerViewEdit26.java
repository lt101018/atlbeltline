package sample;

import connection.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageEventRow25;
import pojo.TransitHistroyRow16;
import pojo.ViewEventsRow26;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

public class ManagerViewEdit26 {

    private static Connection conn;
    //This part is for fxml component
    public Label nameValue;
    public ListView listView;
    public Label startDateValue;
    public Label minStaffValueminStaffValue;
    public Label priceValue;
    public Label endDateValue;
    public Label capacityValue;
    public TextArea descrip;
    public TextField tfvisit2;
    public TextField tfvisit1;
    public TextField tfRevenue1;
    public TextField tfRevenue2;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableView table;

    //This part is for variables
    public static String lastFxml;
    private String eventname;
    private String price;
    private String startdate;
    private String enddate;
    private String minreqstaff;
    private String cap;
    private String sitename;
    public static ManageEventRow25 row25;
    ObservableList<String> list;
    public HashSet<String> originalStaff;

    public void setRow(ManageEventRow25 inputRow) throws SQLException {
        row25 = inputRow;
        eventname = row25.getName();
        startdate = row25.getStartDate();
        sitename = row25.getSiteName();
        getAllParams();
        getAssignedStaff();
    }

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)listView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize() throws SQLException {
        col1.setCellValueFactory(new PropertyValueFactory<>("Date"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Daily Visits"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Daily Revenue"));
        conn = ConnectionManager.getConn();
        originalStaff = new HashSet<>();
    }

    public void getAllParams() throws SQLException {
        Statement statement = conn.createStatement();
        String sqlForEvent = "";
        sqlForEvent = "select name, price, startdate, enddate, minstaffreq, capacity, description from event where sitename = '" +
                sitename + "' and name = '" + eventname +
                "' and startdate = '" + startdate  +
                "'";

        ResultSet resultSet = statement.executeQuery(sqlForEvent);
        while(resultSet.next()) {
            price = resultSet.getString("price");
            enddate = resultSet.getString("enddate");
            minreqstaff = resultSet.getString("minstaffreq");
            cap = resultSet.getString("capacity");
            descrip.setText(resultSet.getString("description"));
        }

        priceValue.setText(price);
        endDateValue.setText(enddate);
        minStaffValueminStaffValue.setText(minreqstaff);
        startDateValue.setText(startdate);
        nameValue.setText(eventname);
        capacityValue.setText(cap);
    }

    public void getAssignedStaff() throws SQLException  {
        Statement statement = conn.createStatement();
        list = FXCollections.observableArrayList();
        listView.setItems(list);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        String sqlForStaffForFree = "(select u.firstname, u.lastname\n" +
                "from user as u, employee as emp, event as e, assign_to as at\n" +
                "where emp.username not in (select staffusername from assign_to) and emp.employeetype = 'staff' and u.status = 'approved' and at.sitename = e.sitename and at.name = e.name and at.startdate = e.startdate and emp.username = u.username)\n";
                //"union\n" +
        String sqlForStaffForThisEvent = "(select u.firstname, u.lastname\n" +
                "from assign_to as at, employee as e, user as u\n" +
                "where u.username = e.username and e.username = at.staffusername and at.sitename = '" +
                sitename + "' and at.name = '" +
                eventname + "' and at.startdate = '" +
                startdate + "')";

        ResultSet resultSet2 = statement.executeQuery(sqlForStaffForThisEvent);
        while (resultSet2.next()) {
            originalStaff.add(resultSet2.getString("firstname") + " " + resultSet2.getString("lastname"));
            list.add(resultSet2.getString("firstname") + " " + resultSet2.getString("lastname"));
        }
        listView.getSelectionModel().selectAll();

        ResultSet resultSet1 = statement.executeQuery(sqlForStaffForFree);
        while (resultSet1.next()) {
            list.add(resultSet1.getString("firstname") + " " + resultSet1.getString("lastname"));
        }

        statement.close();
    }

    public void btnFilter(ActionEvent actionEvent) throws SQLException {
        int visitrange1 = Integer.parseInt(tfvisit1.getText());
        int visitrange2 = Integer.parseInt(tfvisit2.getText());

        double revenuerange1 = Double.parseDouble(tfRevenue1.getText());
        double revenuerange2 = Double.parseDouble(tfRevenue2.getText());

        String sqlForFilter = "select ve.visitdate as date, count(*) as daily_visits, e.price*count(*) as daily_revenues" +
        " from event as e, visit_event as ve" +
        " where" +
        " e.sitename = ve.sitename and e.name = ve.eventname and e.startdate = ve.eventstartdate" +
        " and e.sitename = '"+ sitename +"' and e.name = '"+ eventname +"' " +
        " and e.startdate<=ve.visitdate<=e.enddate" +
        " group by ve.visitdate" +
        " having "+visitrange1+"<daily_visits<"+visitrange2 + " and " + revenuerange1 +"<daily_revenues<" + revenuerange2;

        System.out.println(sqlForFilter);

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlForFilter);
        while(resultSet.next()) {
            addElement(resultSet.getString(1),Integer.parseInt(resultSet.getString(2)),Double.parseDouble(resultSet.getString(3)));
        }
        statement.close();
    }

    public void btnUpdate(ActionEvent actionEvent) throws SQLException {
        Statement statement = conn.createStatement();
        String newDes = descrip.getText();
        String sqlForUpdate = "update event" +
        "set description = '" + newDes +
        "' where event.sitename = '" + sitename + "' and event.name = '" + eventname
                + "' and event.startdate = '" + startdate + "'";
        modifyAssignTo();
        statement.executeUpdate(sqlForUpdate);
        statement.close();
    }

    public void modifyAssignTo() throws SQLException {
        //Below is for adding
        String sqlForInsert = "";
        Statement statement = conn.createStatement();
        ObservableList<String> selectedStaff = listView.getSelectionModel().getSelectedItems();

        for(String staff:selectedStaff) {
            if(!originalStaff.contains(staff)) {
                String firstname = staff.split(" ")[0];
                String lastname = staff.split(" ")[1];
                String username = "";
                String sqlForUsername = "select username from user where firstname = '" + firstname +"' and lastname = '"+ lastname +"'";
                ResultSet resultSet = statement.executeQuery(sqlForUsername);
                while(resultSet.next()) {
                    username = resultSet.getString(1);
                }
                sqlForInsert = "insert into assign_to(staffusername, sitename, name, startdate) values('" + username + "','" + sitename + "','" + nameValue + "','" + startdate + "')";
                System.out.println(sqlForInsert);
                statement.executeUpdate(sqlForInsert);
            }
        }

        for(String s:originalStaff) {
            if(!selectedStaff.contains(s)) {
                String firstname = s.split(" ")[0];
                String lastname = s.split(" ")[1];
                String username = "";
                String sqlForUsername = "select username from user where firstname = '" + firstname +"' and lastname = '"+ lastname +"'";
                System.out.println(sqlForUsername);
                ResultSet resultSet = statement.executeQuery(sqlForUsername);
                while(resultSet.next()) {
                    username = resultSet.getString(1);
                }

                String sqlForDelete = "delete from assign_to where sitename = '" +
                        sitename + "' and name = '" +
                        eventname + "' and startdate = '" +
                        startdate +"' and staffusername ='"+ username +"'";
                System.out.println(sqlForDelete);
                statement.executeUpdate(sqlForDelete);
            }
        }
        statement.close();
    }

    public void addElement(String date, int dailyVisits, double dailyRevenue) {
        ViewEventsRow26 row = new ViewEventsRow26(date, dailyVisits,  dailyRevenue);
        table.getItems().add(row);
    }
}
