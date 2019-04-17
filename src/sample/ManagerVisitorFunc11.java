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
        Parent root = FXMLLoader.load(getClass().getResource("manageevent25.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewStaff(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("managestaff28.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewSiteReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sitereport29.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void takeTransit(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("taketransit15.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewTransitHistory(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("transithistory16.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("userlogin.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void exploreSite(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("exploresite35.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void exploreEvent(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("exploreevent33.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewVisitHistory(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("visitorhistory38.fxml"));
        Stage stage = (Stage)manageProfile.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
