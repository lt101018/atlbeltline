package sample;
import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.TakeTransitRow15;
import pojo.UserInfo;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class TakeTransit15 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public Button filter;
    public DatePicker datepicker;
    public TextField price1;
    public TextField price2;
    public static String lastFxml;
    private static Connection conn;
    public ComboBox cbTransportType;
    public ComboBox cbSite;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize() {
        conn = ConnectionManager.getConn();
        col1.setCellValueFactory(new PropertyValueFactory<>("route"));
        col2.setCellValueFactory(new PropertyValueFactory<>("type"));
        col3.setCellValueFactory(new PropertyValueFactory<>("price"));
        col4.setCellValueFactory(new PropertyValueFactory<>("numSite"));

        cbTransportType.getItems().addAll(
                "ALL",
                "MARTA",
                "Bus",
                "Bike"
        );

        String sql = "select name from site";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            cbSite.getItems().add("ALL");
            while (resultSet.next()) {
                cbSite.getItems().add(resultSet.getString("name"));
            }
            statement.close();
        }catch (SQLException e){
        MyAlert.showAlert(e.getMessage());
        }

        cbSite.getSelectionModel().select(0);
        cbTransportType.getSelectionModel().select(0);
    }


    public void addElement(String route, String type, double price, int numSite) {
        TakeTransitRow15 row = new TakeTransitRow15(route, type, price, numSite);
        table.getItems().add(row);
    }

    public void btnFilter(ActionEvent actionEvent) {

        table.getItems().clear();

        String siteSql = "";
        if(!cbSite.getValue().toString().equals("ALL"))
            siteSql = "where name='"+cbSite.getValue().toString()+"'";

        String transportSql = "";
        String cbContent = cbTransportType.getValue().toString();
        if(cbContent.equals("MARTA") || cbContent.equals("Bus") || cbContent.equals("Bike"))
            transportSql = "and t.type='"+cbTransportType.getValue().toString()+"'";

        String priceSql = "";
        String priceL = "0", priceR = Integer.MAX_VALUE+"";
        if(price1.getText().length()!=0) priceL = price1.getText();
        if(price2.getText().length()!=0) priceR = price2.getText();
        priceSql = "and t.price between "+priceL+" and "+priceR;

        String sql = "select t.route, t.type, t.price, count(case when c.route=t.route then 1 end) as connected_sites\n" +
                "from transit as t, connect as c\n" +
                "where t.type in (select type from connect "+siteSql+") "+transportSql+ priceSql +
                " group by t.route, t.type;";
        System.out.println(sql);
        try{
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            addElement(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4));
        }
        statement.close();
        }catch (SQLException e){
            MyAlert.showAlert(e.getMessage());
        }
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)filter.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnLogTransit(ActionEvent actionEvent) throws SQLException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a transit.");
            return;
        }
        if(datepicker.getValue() == null){
            MyAlert.showAlert("You need to select a date.");
            return;
        }
        TakeTransitRow15 selectedItem = (TakeTransitRow15)table.getSelectionModel().getSelectedItem();
        String formattedDate = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ///following jobs
        Statement statement = conn.createStatement();
        String sqlForCheck = "select *\n" +
                "from take\n" +
                "where username='"+ UserInfo.username +"' and type='" + selectedItem.getType() + "' and  takedate='" + formattedDate + "'" + " and route='" +selectedItem.getRoute()+ "'";
        System.out.println(sqlForCheck);
        ResultSet resultSet = statement.executeQuery(sqlForCheck);
        while(resultSet.next()){
            MyAlert.showAlert("You have taken this transit once that day!!");
            return;
        }

        String sql = "insert into take(username, type, route, takedate) values('"+ UserInfo.username +"','"+ selectedItem.getType() +"', '"+selectedItem.getRoute()+"', '"+formattedDate+"');";
        System.out.println(sql);
        statement.executeUpdate(sql);
        MyAlert.showAlert("Log succeed!!");

        statement.close();
    }

}




