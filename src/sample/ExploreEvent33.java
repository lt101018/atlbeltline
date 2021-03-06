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

    public void initialize()  {
        col1.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        col3.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        col4.setCellValueFactory(new PropertyValueFactory<>("ticketRemaining"));
        col5.setCellValueFactory(new PropertyValueFactory<>("totalVisits"));
        col6.setCellValueFactory(new PropertyValueFactory<>("myVisits"));

        conn = ConnectionManager.getConn();
        String sql = "select name from site";
try {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        cbSiteName.getItems().add("ALL");
        while(resultSet.next()){
            cbSiteName.getItems().add(resultSet.getString("name"));
        }

        cbSiteName.getSelectionModel().select(0);
        statement.close();
    }catch (SQLException e){
        MyAlert.showAlert(e.getMessage());
    }
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

    public void btnFilter(ActionEvent actionEvent) {

        String dateTableSql1 = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            String formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateTableSql1 = " and (v.visitdate between '"+formattedDate1+"'and '"+formattedDate2+"') ";
        }

        String dateTableSql2 = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            String formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateTableSql2 = " and (v.visitdate between '"+formattedDate1+"'and '"+formattedDate2+"') ";
        }


        table.getItems().clear();
        String sql = "CREATE   or replace view eventtotalvisit AS\n" +
                "SELECT e.name,e.sitename,e.price,e.startdate,count(case when e.name=v.eventname and e.sitename=v.sitename and e.startdate=v.eventstartdate "+dateTableSql1+" then 1 end) as totalvisit\n" +
                "from event as e\n" +
                "left join visit_event as v\n" +
                "on e.name=v.eventname and e.sitename=v.sitename and e.startdate=v.eventstartdate\n" +
                "group by e.name,e.sitename,e.startdate;";
        System.out.println(sql);
        try {
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        sql = "CREATE   or replace view eventmyvisit AS\n" +
                "SELECT e.name,e.sitename,e.price,e.startdate,count(case when e.name=v.eventname and e.sitename=v.sitename and e.startdate=v.eventstartdate and v.visitorusername='"+UserInfo.username+"' "+dateTableSql2+" then 1 end) as myvisit\n" +
                "from event as e\n" +
                "left join visit_event as v\n" +
                "on e.name=v.eventname and e.sitename=v.sitename and e.startdate=v.eventstartdate\n" +
                "group by e.name,e.sitename,e.startdate;";
        statement.executeUpdate(sql);
        System.out.println(sql);
        String eventNameSql = " 1=1 ";
        if(tfname.getText().length()!=0)
            eventNameSql = " e.name like '%"+tfname.getText()+"%' ";

        String descriptionSql = "";
        if(tfKeyword.getText().length()!=0)
            descriptionSql = " and  e.description like '%"+tfKeyword.getText()+"%' ";

        String dateSql1 = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            String formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateSql1 = " and not (e.startdate>'"+formattedDate2+"' or e.enddate<'"+formattedDate1+"') ";
        }

        String siteSql = "";
        if(!cbSiteName.getValue().toString().equals("ALL")){
            siteSql = " and e.sitename='"+cbSiteName.getValue().toString()+"' ";
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
        if(!checkSoldOut.isSelected())
            soldOutSql = " and (e.capacity-etv.totalvisit)!='0' ";

        String myVisitSql = "";
        if(!checkVisited.isSelected())
            myVisitSql = " and emv.myvisit ='0' " ;



        sql = "select e.name,e.sitename,e.price, (e.capacity-etv.totalvisit)as ticketremaining, etv.totalvisit,emv.myvisit, e.startdate,e.enddate,e.description\n" +
                "from event as e\n" +
                "left join eventtotalvisit as etv on e.name=etv.name and e.sitename=etv.sitename and e.startdate=etv.startdate\n" +
                "left join eventmyvisit as emv on e.name=emv.name and e.sitename=emv.sitename and e.startdate=emv.startdate\n" +
                "where "+eventNameSql+" "+descriptionSql+" "+siteSql +
                dateSql1 +
                " and e.price between '"+priceL+"' and '"+priceR+"' " +
                " and etv.totalvisit between '"+visitL+"' and '"+visitR+"' " +
                soldOutSql+
                myVisitSql+
                " group by e.name,e.sitename,e.startdate;";

        System.out.println(sql);

        ResultSet resultSet =  statement.executeQuery(sql);
        while(resultSet.next()){
            addElement(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4),resultSet.getInt(5),resultSet.getInt(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9));
        }
        statement.close();
    }catch (SQLException e){
        MyAlert.showAlert(e.getMessage());
    }
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
