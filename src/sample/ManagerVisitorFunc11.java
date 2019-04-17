package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerVisitorFunc11 {
    public Button manageProfile;

    public void manageProfile(ActionEvent actionEvent) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("manageprofile17.fxml"));
//        Stage stage = (Stage)manageProfile.getScene().getWindow();
//        stage.setScene(new Scene(root));
    }

    public void manageEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manageevent25.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ManageEvent25 controller = fxmlLoader.getController();
        controller.setLastFxml("managervisitorfunc11.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewStaff(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("managestaff28.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ManageStaff28 controller = fxmlLoader.getController();
        controller.setLastFxml("managervisitorfunc11.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewSiteReport(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sitereport29.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        SiteReport29 controller = fxmlLoader.getController();
        controller.setLastFxml("managervisitorfunc11.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void takeTransit(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("taketransit15.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        TakeTransit15 controller = fxmlLoader.getController();
        controller.setLastFxml("managervisitorfunc11.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewTransitHistory(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("transithistory16.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        TransitHistory16 controller = fxmlLoader.getController();
        controller.setLastFxml("managervisitorfunc11.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("userlogin.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void exploreSite(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("exploresite35.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ExploreSite35 controller = fxmlLoader.getController();
        controller.setLastFxml("managervisitorfunc11.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void exploreEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("exploreevent33.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ExploreEvent33 controller = fxmlLoader.getController();
        controller.setLastFxml("managervisitorfunc11.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewVisitHistory(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("visitorhistory38.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        VisitorHistory38 controller = fxmlLoader.getController();
        controller.setLastFxml("managervisitorfunc11.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
