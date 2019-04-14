package sample;

import connection.ConnectionManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import connection.ConnectionManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ConnectionManager.init();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));

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
