package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageEventRow25;
import pojo.ManageTransitRow22;
import pojo.UserInfo;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class ManageEvent25 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TextField tfKeyword;
    public TextField tfRevenue1;
    public TextField tfRevenue2;
    public TextField tfduration1;
    public TextField tfduration2;
    public TextField tfTotalVisits1;
    public TextField tfTotalVisits2;
    public TextField tfname;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public static String lastFxml;

    private static Connection conn;
    public TableColumn col6;
    public TableColumn col7;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("staffCount"));
        col3.setCellValueFactory(new PropertyValueFactory<>("duration"));
        col4.setCellValueFactory(new PropertyValueFactory<>("totalVisits"));
        col5.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
        col6.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        col7.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        conn = ConnectionManager.getConn();
    }

    public void addElement(String name, int staffCount, int duration, int totalVisits, double totalRevenue, String siteName, String startDate) {
        ManageEventRow25 row = new ManageEventRow25(name, staffCount, duration,totalVisits,totalRevenue, siteName, startDate);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) {
        table.getItems().clear();

        String eventSql = "";
        if(tfname.getText().length()!=0)
            eventSql = "and e.name like '%"+tfname.getText()+"%'";

        String descriptionSql = "";
        if(tfKeyword.getText().length()!=0)
            descriptionSql = "and e.description like '%"+tfKeyword.getText()+"%'";

        String dateSql = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            String formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateSql = "and e.startdate >= '"+formattedDate1+"' and e.enddate <= '"+formattedDate2+"'";
        }

        String durationL = "0", durationR = Integer.MAX_VALUE+"";
        if(tfduration1.getText().length()!=0)
            durationL = tfduration1.getText();
        if(tfduration2.getText().length()!=0)
            durationR = tfduration2.getText();
        String dateDifSql = "and datediff(e.enddate, e.startdate) between "+durationL+" and "+durationR;

        String visitL = "0", visitR = Integer.MAX_VALUE+"";
        if(tfTotalVisits1.getText().length()!=0)
            visitL = tfTotalVisits1.getText();
        if(tfTotalVisits2.getText().length()!=0)
            visitR = tfTotalVisits2.getText();
        String revenueL = "0", revenueR = Integer.MAX_VALUE+"";
        if(tfRevenue1.getText().length()!=0)
            revenueL = tfRevenue1.getText();
        if(tfRevenue2.getText().length()!=0)
            revenueR = tfRevenue2.getText();
        String visitRevenueSql = "having ( count(*) between "+visitL+" and "+visitR+" ) and ( " + "total_revenues between "+revenueL+" and "+revenueR+" )";

        String sql = "select e.name, datediff(e.enddate, e.startdate) as duration , count(*) as total_visits, e.price*count(*) as total_revenues, e.sitename, e.startdate\n" +
                "from visit_event as ve, event as e\n" +
                "where ve.sitename = e.sitename and ve.eventname = e.name and ve.eventstartdate = e.startdate "+eventSql+" "+descriptionSql+" "+dateSql+" "+dateDifSql+" and e.sitename in " +
                "( select s.name\n" +
                "from site as s\n" +
                "where s.managerusername = '"+ UserInfo.username +"')" +
                "group by ve.sitename, ve.eventname, ve.eventstartdate\n" +
                visitRevenueSql;
        System.out.println(sql);
try {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            String sql1 = "select count(*) as staff_count\n" +
                    "from assign_to as at\n" +
                    "where at.sitename = '"+resultSet.getString(5)+"' and at.name = '"+resultSet.getString(1)+"' and at.startdate = '"+resultSet.getString(6)+"'\n" +
                    "group by at.sitename, at.name, at.startdate;";
            System.out.println(sql);
            Statement statement1 = conn.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);
            if(resultSet1.next()){
                addElement(resultSet.getString(1),resultSet1.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getDouble(4),resultSet.getString(5),resultSet.getString(6));
            }
            statement1.close();
        }
        statement.close();
    }catch (SQLException e){
        MyAlert.showAlert(e.getMessage());
    }
    }

    public void btnEdit(ActionEvent actionEvent) throws IOException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select an event.");
            return;
        }
        ManageEventRow25 selectedItem = (ManageEventRow25)table.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem.getSiteName()+selectedItem.getStartDate());
        //go 26
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("managerviewedit26.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ManagerViewEdit26 controller = fxmlLoader.getController();
        controller.setRow(selectedItem);

        controller.setLastFxml("manageevent25.fxml");
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnDelete(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select an event.");
            return;
        }
        ManageEventRow25 selectedItem = (ManageEventRow25)table.getSelectionModel().getSelectedItem();
        ///following jobs

        String sql = "delete from event where sitename = '"+selectedItem.getSiteName()+"' and name = '"+selectedItem.getName()+"' and startdate = '"+selectedItem.getStartDate()+"';";
try {
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }catch (SQLException e){
        MyAlert.showAlert(e.getMessage());
    }
        btnFilter(null);
        MyAlert.showAlert("Event deleted.");
    }

    public void btnCreate(ActionEvent actionEvent) throws IOException {
        //go 27
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("managercreateevent27.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ManagerCreateEvent27 controller = fxmlLoader.getController();
        controller.setLastFxml("manageevent25.fxml");
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
