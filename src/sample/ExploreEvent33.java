package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ExploreEventRow33;
import pojo.UserInfo;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class ExploreEvent33 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TableColumn col6;
    public TextField tfKeyword;
    public TextField tfPriceRange1;
    public TextField tfPriceRange2;
    public TextField tfTotalVisits1;
    public TextField tfTotalVisits2;
    public TextField tfname;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public CheckBox checkVisited;
    public CheckBox checkSoldOut;

    private static Connection conn;

    public static String lastFxml;
    public ComboBox cbSiteName;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize() throws SQLException {
        col1.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        col3.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        col4.setCellValueFactory(new PropertyValueFactory<>("ticketRemaining"));
        col5.setCellValueFactory(new PropertyValueFactory<>("totalVisits"));
        col6.setCellValueFactory(new PropertyValueFactory<>("myVisits"));

        conn = ConnectionManager.getConn();
        String sql = "select name from site";

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        cbSiteName.getItems().add("ALL");
        while(resultSet.next()){
            cbSiteName.getItems().add(resultSet.getString("name"));
        }

        cbSiteName.getSelectionModel().select(0);
        statement.close();
    }

    public void addElement(String eventName, String siteName, double ticketPrice, int ticketRemaining, int totalVisits, int myVisits, String startDate, String endDate, String description) {
        ExploreEventRow33 row = new ExploreEventRow33(eventName, siteName, ticketPrice,ticketRemaining,totalVisits,myVisits,startDate,endDate,description);
        table.getItems().add(row);
    }


    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) throws SQLException {

        table.getItems().clear();
        String sql = "create or replace view eventtotalvisit as\n" +
                "select eventname,sitename,eventstartdate,count(*) as totalvisit\n" +
                "from visit_event\n" +
                "group by eventname,sitename,eventstartdate;";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);

        String eventNameSql = "";
        if(tfname.getText().length()!=0)
            eventNameSql = "and e.name like '%"+tfname.getText()+"%'";

        String descriptionSql = "";
        if(tfKeyword.getText().length()!=0)
            descriptionSql = "and  e.description like '%"+tfKeyword.getText()+"%'";

        String dateSql1 = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            String formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateSql1 = "and e.startdate>='"+formattedDate1+"' and e.enddate<='"+formattedDate2+"'";
        }

        String siteSql = "";
        if(!cbSiteName.getValue().toString().equals("ALL")){
            siteSql = "and e.sitename='"+cbSiteName.getValue().toString()+"'\n";
        }

        String visitL = "0", visitR = Integer.MAX_VALUE+"";
        if(tfTotalVisits1.getText().length()!=0)
            visitL = tfTotalVisits1.getText();
        if(tfTotalVisits2.getText().length()!=0)
            visitR = tfTotalVisits2.getText();
        String priceL = "0", priceR = Integer.MAX_VALUE+"";
        if(tfPriceRange1.getText().length()!=0)
            priceL = tfPriceRange1.getText();
        if(tfPriceRange2.getText().length()!=0)
            priceR = tfPriceRange2.getText();

        String soldOutSql = "";
        if(checkSoldOut.isSelected())
            soldOutSql = "and (e.capacity-etv.totalvisit)!='0'";


        String eventNameSql1 = "where 1=1";
        if(tfname.getText().length()!=0)
            eventNameSql1 = "where ev.name like '%"+tfname.getText()+"%'";

        String descriptionSql1 = "";
        if(tfKeyword.getText().length()!=0)
            descriptionSql1 = "and  ev.description like '%"+tfKeyword.getText()+"%'";

        String dateSql11 = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            String formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateSql11 = "and ev.startdate>='"+formattedDate1+"' and ev.enddate<='"+formattedDate2+"'";
        }

        sql = "select e.name, e.sitename,e.price,(e.capacity-etv.totalvisit) as ticket_remaining,etv.totalvisit, count(*)as myvisits, e.startdate, e.enddate, e.description\n" +
                "from event as e, visit_event as v,eventtotalvisit as etv\n" +
                "where e.name =v.eventname\n" +
                "and e.sitename=v.sitename \n" +
                "and e.startdate=v.eventstartdate\n" +
                eventNameSql+" "+descriptionSql+"  "+dateSql1 +" "+siteSql+
                "and etv.totalvisit>='"+visitL+"' and etv.totalvisit<='"+visitR+"'\n" +
                "and e.price>='"+priceL+"'and e.price<='"+priceR+"' " + soldOutSql+" "+
                "and e.name =v.eventname\n" +
                "and e.sitename=v.sitename \n" +
                "and e.startdate=v.eventstartdate\n" +
                "and e.name in (select ev.name from event as ev "+eventNameSql1+"  "+descriptionSql1+" "+dateSql11+" ) \n" +
                "and v.visitorusername='"+ UserInfo.username +"'\n" +
                "and e.name=etv.eventname\n" +
                "and e.sitename=etv.sitename\n" +
                "and e.startdate=etv.eventstartdate\n" +
                "group by v.eventname,v.sitename,v.eventstartdate;";

        System.out.println(sql);
        ResultSet resultSet =  statement.executeQuery(sql);
        while(resultSet.next()){
            addElement(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4),resultSet.getInt(5),resultSet.getInt(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9));
        }
        statement.close();
    }

    public void btnEventDetail(ActionEvent actionEvent) throws IOException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select an event.");
            return;
        }
        ExploreEventRow33 selectedItem = (ExploreEventRow33)table.getSelectionModel().getSelectedItem();
        ///following jobs

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("visitoreventdetail34.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        VisitorEventDetail34 controller = fxmlLoader.getController();
        controller.setLastFxml("exploreevent33.fxml");
        controller.setLabels(selectedItem.getEventName(),selectedItem.getSiteName(),selectedItem.getTicketPrice(),selectedItem.getTicketRemaining(),selectedItem.getStartDate(),selectedItem.getEndDate(),selectedItem.getDescription());
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
