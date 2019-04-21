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
import pojo.UserInfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class ManagerCreateEvent27 {

    public ListView listView;
    public Button backBttn;
    public Button createBttn;
    public TextArea descriptionValue;
    public TextField nameValue;
    public TextField priceValue;
    public TextField capValue;
    public TextField minStaffValue;
    public DatePicker startdateValue;
    public DatePicker endDateValue;
    private static Connection conn;

    public String sitename;
    public Button filterBttn;

    ObservableList<String> list;


    public static String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

//    insert into event(sitename, name, startdate, enddate, price, capacity, description, ministaffreq)
//    values('', 'walking tour','2019-01-01','2019-02-03',2.0, 20, 'description',1);
//-- display staff avaliable
//    select u.firstname, u.lastname, u.username -- 前两个是作为显示的名字，
//    最后一个是作为staffusername新插入assign_to表中，见下一个statement
//    from user as u, employee as emp, event as e, assign_to as at
//    where emp.username not in (select staffusername from assign_to) and
//    emp.employeetype = 'staff' and u.status = 'approved' and
//    at.sitename = e.sitename and at.name = e.name and at.startdate = e.startdate
//    and at.staffusername = emp.username and emp.username = u.username and e.sitename = ''
//    and e.name = '' and e.startdate = ''; -- 对用户刚创建的新条目进行操作
//
//    insert into assign_to(staffusername, sitename, name, startdate) values('','','',''); -- 把该插的都插进去

    public void initialize() throws SQLException {
        conn = ConnectionManager.getConn();
        Statement statement = conn.createStatement();
        /*
        * Below is for sitename
        * */
        sitename = "";
        String username = UserInfo.username;
        String sqlForSiteName = "select name from site where managerusername = '" + username + "'";

        ResultSet resultSet = statement.executeQuery(sqlForSiteName);
        while(resultSet.next()){
            sitename = resultSet.getString(1);
        }

        System.out.println("Sitename is " + sitename);
        statement.close();

    }

    public void createEvent(ActionEvent actionEvent) throws IOException, SQLException {
        String eventName = nameValue.getText();
        String price = priceValue.getText();
        String cap = capValue.getText();
        String min = minStaffValue.getText();
        String startdate = startdateValue.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String enddate = endDateValue.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String descrp = descriptionValue.getText();
        String sqlForInsertingEvent = "insert into event(sitename, name, startdate, enddate, price, capacity, description, minstaffreq)" +
                "values\n('" +
                sitename + "',\n'" +
                eventName + "',\n'" +
                startdate + "',\n'" +
                enddate + "',\n'" +
                price + "',\n'" +
                cap + "',\n'" +
                descrp + "',\n'" +
                min + "')";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sqlForInsertingEvent);
        statement.close();
        createAssignTo();
    }

    public void createAssignTo () throws SQLException {
        String eventName = nameValue.getText();
        String startdate = startdateValue.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sqlForInsert = "";
        Statement statement = conn.createStatement();
        ObservableList<String> selectedStaff = listView.getSelectionModel().getSelectedItems();
        for(String staff:selectedStaff) {
            sqlForInsert = "insert into assign_to(staffusername, sitename, name, startdate) values('" + staff + "','" + sitename + "','" + eventName + "','" + startdate + "')";
            statement.executeUpdate(sqlForInsert);
        }
        statement.close();
        // insert into assign_to(staffusername, sitename, name, startdate) values('','','','')

    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)listView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    public void filterStaff(ActionEvent actionEvent) throws SQLException {
        Statement statement = conn.createStatement();
        //*******Populate the listview*******
        String startdate = startdateValue.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String enddate = endDateValue.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        list = FXCollections.observableArrayList();
        listView.setItems(list);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

//        String sql = "u.firstname, u.lastname, u.username from user as u, employee as emp, event as e, assign_to as at" +
//                " where emp.username not in (select staffusername from assign_to) and emp.employeetype = 'staff'" +
//                "and u.status = 'approved' and at.sitename = e.sitename and at.name = e.name and at.startdate = e.startdate and at.staffusername = emp.username and emp.username = u.username" +
//                " and e.sitename = " + sitename + " and " + "e.name = " + "";
        //String sql = "select u.firstname, u.lastname, u.username from user as u";
        //'' and e.name = '' and e.startdate = ''; -- 对用户刚创建的新条目进行操作
        String sql = "select u.firstname, u.lastname, u.username\n" +
                "from user as u, employee as emp, event as e, assign_to as at\n" +
                " where emp.username not in (select staffusername from assign_to) and emp.employeetype = 'staff'\n" +
                "and u.status = 'approved' and at.sitename = e.sitename and at.name = e.name and at.startdate = e.startdate\n" +
                "and at.staffusername = emp.username and emp.username = u.username and (e.enddate<'" + startdate + "' or e.startdate>'" + enddate + "')";
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            System.out.println(list);
            list.add(resultSet.getString("firstname") + " " + resultSet.getString("lastname"));
        }
        statement.close();
        //*******Populate the listview*******
    }
}
