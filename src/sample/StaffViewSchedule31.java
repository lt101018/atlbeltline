package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.StaffViewScheduleRow31;
import pojo.UserInfo;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class StaffViewSchedule31 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TextField tfDescriptionKeyword;
    public TextField tfEventName;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public static String lastFxml;
    private static Connection conn;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        col3.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        col4.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        col5.setCellValueFactory(new PropertyValueFactory<>("staffCount"));
        conn = ConnectionManager.getConn();
    }

    public void addElement(String eventName, String siteName, String startDate, String endDate, int staffCount) {
        StaffViewScheduleRow31 row = new StaffViewScheduleRow31(eventName, siteName, startDate,endDate,staffCount);
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
        if(tfEventName.getText().length()!=0){
            eventSql = "and e.name like '%"+tfEventName.getText()+"%'";
        }

        String descriptionSql = "";
        if(tfDescriptionKeyword.getText().length()!=0){
            descriptionSql = "and  e.description like '%"+tfDescriptionKeyword.getText()+"%'";
        }

        String dateSql = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            String formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateSql = "and e.startdate>='"+formattedDate1+"' and e.enddate<='"+formattedDate2+"'";
        }


        String eventSql1 = "1=1";
        if(tfEventName.getText().length()!=0){
            eventSql1 = "ev.name like '%"+tfEventName.getText()+"%'";
        }

        String descriptionSql1 = "";
        if(tfDescriptionKeyword.getText().length()!=0){
            descriptionSql1 = "and  ev.description like '%"+descriptionSql1+"%'";
        }

        String dateSql1 = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            String formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateSql1 = "  and not (e.startdate>'"+formattedDate2+"' or e.enddate<'"+formattedDate1+"')  ";
        }

        String sql = "select e.name, e.sitename,e.startdate,e.enddate,count(*)as staffcount\n" +
                "from event as e, assign_to as a\n" +
                "where e.name =a.name and e.sitename=a.sitename "+eventSql+"  "+descriptionSql+" "+dateSql+" and e.sitename in (select ev.sitename from event as ev where "+eventSql1+"  "+descriptionSql1+" "+dateSql1+" ) and e.startdate=a.startdate and a.staffusername = '"+ UserInfo.username +"' " +
                "group by a.sitename,a.name,a.startdate;";
        System.out.println(sql);
try {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            addElement(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5));
        }
        statement.close();
    }catch (SQLException e){
        MyAlert.showAlert(e.getMessage());
    }
    }

    public void btnViewEvent(ActionEvent actionEvent) throws IOException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select an event.");
            return;
        }
        StaffViewScheduleRow31 selectedItem = (StaffViewScheduleRow31)table.getSelectionModel().getSelectedItem();
        ///following jobs

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("staffeventdetail32.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        StaffEventDetail32 controller = fxmlLoader.getController();
        controller.setLastFxml("staffviewschedule31.fxml");
        controller.setLabels(selectedItem.getSiteName(), selectedItem.getEventName(), selectedItem.getStartDate(), selectedItem.getEndDate());
        controller.initialize1();
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
