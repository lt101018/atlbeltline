package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ExploreSiteRow35;
import tools.MyAlert;

import java.io.IOException;

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

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("eventCount"));
        col3.setCellValueFactory(new PropertyValueFactory<>("totalVisits"));
        col4.setCellValueFactory(new PropertyValueFactory<>("myVisits"));
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

    public void btnFilter(ActionEvent actionEvent) {
        ////test below
        addElement("yoyoyo",1,5,7);
        addElement("heyyyy",3,6,1);
        ///test above
    }

    public void btnSiteDetail(ActionEvent actionEvent) throws IOException {
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
