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

public class VisitorEventDetail34 {
    public DatePicker datepicker;
    public Label labelSite;
    public Label labelEvent;
    public Label labelStartDate;
    public Label labelEndDate;
    public Label labelTicketPrice;
    public Label labelTicketRemaining;
    public Label description;
    public static String lastFxml;
    private static Connection conn;
    public void initialize(){
        conn = ConnectionManager.getConn();
    }

    public void setLabels(String eventName, String siteName, double ticketPrice, int ticketRemaining, String startDate, String endDate, String thedescription) {
        labelEvent.setText(eventName);
        labelSite.setText(siteName);
        labelTicketPrice.setText(ticketPrice+"");
        labelTicketRemaining.setText(ticketRemaining+"");
        labelStartDate.setText(startDate);
        labelEndDate.setText(endDate);
        description.setText(thedescription);
    }
    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)labelSite.getScene().getWindow();
        stage.setScene(new Scene(root));
    }



    public void btnLogVisit(ActionEvent actionEvent) {
        if(datepicker.getValue()==null){
            MyAlert.showAlert("Please select time.");
            return;
        }

        if(labelTicketRemaining.getText().equals("0")){
            MyAlert.showAlert("Sold Out.");
            return;
        }

        String formattedDate = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String sql = "select * \n" +
                "from visit_event\n" +
                "where visitorusername='"+ UserInfo.username +"' and eventname='"+labelEvent.getText()+"' and sitename='"+labelSite.getText()+"' and visitdate='"+formattedDate+"';";
try {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            MyAlert.showAlert("You already have logged in the same day.");
            return;
        }

        sql = "insert into visit_event (visitorusername,sitename,eventname,eventstartdate,visitdate) " +
                "values('"+UserInfo.username+"','"+labelSite.getText()+"','"+labelEvent.getText()+"','"+labelStartDate.getText()+"','"+formattedDate+"');";

        System.out.println(sql);
        statement = conn.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }catch (SQLException e){
        MyAlert.showAlert(e.getMessage());
    }

        MyAlert.showAlert("Visit log success.");
    }
}
