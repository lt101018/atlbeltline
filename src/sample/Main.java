package sample;

import connection.ConnectionManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ConnectionManager.init();
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("userlogin.fxml"));
        System.out.println("Start Main!");
        primaryStage.setTitle("Atlanta Beltline");
        primaryStage.setScene(new Scene(root));

        primaryStage.setOnHidden(e -> {
            ConnectionManager.close();
            Platform.exit();
        });

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
