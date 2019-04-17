package tools;

import javafx.scene.control.Alert;

public class MyAlert {
    public static void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
