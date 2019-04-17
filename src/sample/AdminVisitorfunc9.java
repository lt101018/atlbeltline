package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminVisitorfunc9 {
    public Button manageProfile;

    public void manageProfile(ActionEvent actionEvent) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("17.fxml"));
//        Stage stage = (Stage)manageProfile.getScene().getWindow();
//        stage.setScene(new Scene(root));
    }

    public void takeTransit(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("taketransit15.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        TakeTransit15 controller = fxmlLoader.<TakeTransit15>getController();
        controller.setLastFxml("adminvisitorfunc9.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void manageUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manageuser18.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ManageUser18 controller = fxmlLoader.<ManageUser18>getController();
        controller.setLastFxml("adminvisitorfunc9.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewTransitHistory(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("transithistory16.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        TransitHistory16 controller = fxmlLoader.<TransitHistory16>getController();
        controller.setLastFxml("adminvisitorfunc9.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void manageTransit(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("managetransit22.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ManageTransit22 controller = fxmlLoader.<ManageTransit22>getController();
        controller.setLastFxml("adminvisitorfunc9.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("userlogin.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void manageSite(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("managesite19.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ManageSite19 controller = fxmlLoader.<ManageSite19>getController();
        controller.setLastFxml("adminvisitorfunc9.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void exploreSite(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("exploresite35.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ExploreSite35 controller = fxmlLoader.<ExploreSite35>getController();
        controller.setLastFxml("adminvisitorfunc9.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void exploreEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("exploreevent33.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        ExploreEvent33 controller = fxmlLoader.<ExploreEvent33>getController();
        controller.setLastFxml("adminvisitorfunc9.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewVisitHistory(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("visitorhistory38.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        VisitorHistory38 controller = fxmlLoader.<VisitorHistory38>getController();
        controller.setLastFxml("adminvisitorfunc9.fxml");
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
