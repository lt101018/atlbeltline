package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StaffEventDetail32 {
    public Label labelSite;
    public Label labelEvent;
    public Label labelStartDate;
    public Label labelEndDate;
    public Label labelPrice;
    public Label labelCapacity;
    public Label description;
    public Label labelDurationDays;
    public static String lastFxml;

    private static Connection conn;
    public MenuButton mbStaff;

    public void initialize(){
        conn = ConnectionManager.getConn();
        description.setWrapText(true);
        mbStaff.setText("Click");
    }

    public void initialize1() throws SQLException {
        String sql = "select datediff(e.enddate, e.startdate) as duration, e.capacity,e.price,e.description\n" +
                "from event as e\n" +
                "where e.name='"+labelEvent.getText()+"' and e.sitename='"+labelSite.getText()+"' and e.startdate='"+labelStartDate.getText()+"';";
        System.out.println(sql);

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            labelDurationDays.setText(resultSet.getString(1));
            labelCapacity.setText(resultSet.getString(2));
            labelPrice.setText(resultSet.getString(3));
            description.setText(resultSet.getString(4));
        }

        sql = "select distinct u.firstname,u.lastname\n" +
                "from user as u, assign_to as a\n" +
                "where u.username=a.staffusername and a.staffusername in(select staffusername from assign_to where name='"+labelEvent.getText()+"' and sitename='"+labelSite.getText()+"' and startdate='"+labelStartDate.getText()+"')\n" +
                "order by u.firstname;";

        System.out.println(sql);

        statement = conn.createStatement();
        ResultSet resultSet1 = statement.executeQuery(sql);
        while(resultSet1.next()){
            mbStaff.getItems().add(new MenuItem(resultSet1.getString(1)+" "+resultSet1.getString(2)));
        }

        statement.close();
    }

    public void setLabels(String site, String event, String startDate, String endDate) {
        labelSite.setText(site);
        labelEvent.setText(event);
        labelStartDate.setText(startDate);
        labelEndDate.setText(endDate);
    }

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)labelSite.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
