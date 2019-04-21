package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ExploreSiteRow35;
import pojo.UserInfo;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class ExploreSite35 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TextField tfEventCountRange1;
    public TextField tfEventCountRange2;
    public TextField tfTotalVisits1;
    public TextField tfTotalVisits2;
    public DatePicker datepicker1;
    public DatePicker datepicker2;
    public MenuButton menuName;
    public CheckBox checkVisited;
    public static String lastFxml;
    public ComboBox cbName;
    public ComboBox cbOpenEveryday;

    private static Connection conn;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize() throws SQLException {
        col1.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("eventCount"));
        col3.setCellValueFactory(new PropertyValueFactory<>("totalVisits"));
        col4.setCellValueFactory(new PropertyValueFactory<>("myVisits"));
        cbOpenEveryday.getItems().addAll(
                "ALL",
                "Yes",
                "No"
        );

        conn = ConnectionManager.getConn();
        String sql = "select name from site";

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        cbName.getItems().add("ALL");
        while(resultSet.next()){
            cbName.getItems().add(resultSet.getString("name"));
        }

        cbName.getSelectionModel().select(0);
        cbOpenEveryday.getSelectionModel().select(0);
        statement.close();

    }

    public void addElement(String siteName, int eventCount, int totalVisits, int myVisits) {
        ExploreSiteRow35 row = new ExploreSiteRow35(siteName, eventCount,totalVisits,myVisits);
        table.getItems().add(row);
    }


    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) throws SQLException {


        String formattedDate1 = "";
        String formattedDate2 = "";
        String dateTableSql1 = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            formattedDate1 = datepicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            formattedDate2 = datepicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateTableSql1 = " and (v.visitdate between '"+formattedDate1+"'and '"+formattedDate2+"') ";
        }

        String dateTableSql2 = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            dateTableSql2 = " and (v.visitdate between '"+formattedDate1+"' and '"+formattedDate2+"') ";
        }

        String dateTableSql3 = "";
        if(datepicker1.getValue()!=null && datepicker2.getValue()!=null){
            dateTableSql3 = " and not (v.startdate>'"+formattedDate2+"' or v.enddate<'"+formattedDate1+"') ";
        }


        table.getItems().clear();
        String sql = "CREATE  or replace view sitetotalvisit AS\n" +
                "SELECT s.name,count(case when s.name=v.name "+dateTableSql1+" then 1 end) as totalvisit\n" +
                "from site as s\n" +
                "left join visit_site as v\n" +
                "on s.name=v.name \n" +
                "group by s.name;";
        System.out.println(sql);
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);

        sql = "CREATE  or replace view sitemyvisit AS\n" +
                "SELECT s.name,count(case when s.name=v.name and v.visitorusername='"+UserInfo.username+"' "+dateTableSql2+" then 1 end) as myvisit\n" +
                "from site as s\n" +
                "left join visit_site as v\n" +
                "on s.name=v.name \n" +
                "group by s.name;";
        statement.executeUpdate(sql);
        System.out.println(sql);

        sql = "CREATE  or replace view eventcount35 AS\n" +
                "SELECT s.name,count(case when s.name=v.sitename "+dateTableSql3+" then 1 end) as eventcount\n" +
                "from site as s, event as v\n" +
                "group by s.name;";
        statement.executeUpdate(sql);
        System.out.println(sql);


        String siteSql = " 1=1 ";
        if(!cbName.getValue().toString().equals("ALL")){
            siteSql = " s.name='"+cbName.getValue().toString()+"' ";
        }

        String openEveryDaySql = "";
        if(!cbOpenEveryday.getValue().toString().equals("ALL")){
            openEveryDaySql = " and s.openeveryday='"+cbOpenEveryday.getValue().toString()+"' ";
        }

        String totalVisitL = "0", totalVisitR = Integer.MAX_VALUE+"";
        if(tfTotalVisits1.getText().length()!=0)
            totalVisitL = tfTotalVisits1.getText();
        if(tfTotalVisits2.getText().length()!=0)
            totalVisitR = tfTotalVisits2.getText();

        String eventcountL = "0", eventcountR = Integer.MAX_VALUE+"";
        if(tfEventCountRange1.getText().length()!=0)
            eventcountL = tfTotalVisits1.getText();
        if(tfEventCountRange1.getText().length()!=0)
            eventcountR = tfTotalVisits2.getText();

        String visitedSql = "";
        if(!checkVisited.isSelected())
            visitedSql = " and smv.myvisit= '0' " ;

        sql = "select s.name,ec.eventcount,stv.totalvisit,smv.myvisit\n" +
                "from site as s\n" +
                "left join sitetotalvisit as stv on s.name=stv.name\n" +
                "left join sitemyvisit  as smv on s.name=smv.name\n" +
                "left join eventcount35 as  ec on s.name=ec.name\n" +
                "where " +siteSql + openEveryDaySql +
                "and stv.totalvisit between '"+totalVisitL+"' and '"+totalVisitR+"'\n" +
                "and ec.eventcount between '"+eventcountL+"' and '"+eventcountR+"'\n" +
                visitedSql;

        System.out.println(sql);
        ResultSet resultSet =  statement.executeQuery(sql);
        while(resultSet.next()){
            addElement(resultSet.getString(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4));
        }
        statement.close();


    }

    public void btnSiteDetail(ActionEvent actionEvent) throws IOException, SQLException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a site.");
            return;
        }
        ExploreSiteRow35 selectedItem = (ExploreSiteRow35)table.getSelectionModel().getSelectedItem();
        ///following jobs

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sitedetail37.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        SiteDetail37 controller = fxmlLoader.getController();
        controller.setLastFxml("exploresite35.fxml");
        controller.setSite(selectedItem.getSiteName());
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnTransitDetail(ActionEvent actionEvent) throws IOException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a site.");
            return;
        }
        ExploreSiteRow35 selectedItem = (ExploreSiteRow35)table.getSelectionModel().getSelectedItem();
        ///following jobs

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("transitdetail36.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        TransitDetail36 controller = fxmlLoader.getController();
        controller.setLastFxml("exploresite35.fxml");
        controller.setSite(selectedItem.getSiteName());
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
