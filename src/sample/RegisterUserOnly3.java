package sample;

import connection.ConnectionManager;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Alert.AlertType;
import javafx.concurrent.Service;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tools.MyAlert;

public class RegisterUserOnly3 {


    @FXML
    public Button registerBttn;

    @FXML
    public Button cancelBttn;

    public TextField usernameTF;
    public TextField passwordTF;
    public TextField firstNameTF;
    public TextField lastNameTF;
    public TextField comfirmpasswordTF;


    public ArrayList<String> emailList;

    public int emailNumber;

    private static Connection conn;
    public static String lastFxml;
    public TextArea emailsTA;
    public Button updateEmailBttn;
    public Button editBttn;
    public Label emailLael;
    public Label updatedemails;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize() {
        emailList = new ArrayList<>();
        emailNumber = 0;
        System.out.println("Controller initializing for user!!");
        conn = ConnectionManager.getConn();
    }

    public void ensureUpdateEmail(ActionEvent actionEvent) throws SQLException{
        String read_wroteemails = "";
        read_wroteemails = emailsTA.getText();
        String[] email_string = read_wroteemails.split(",");

        for(int i=0; i<email_string.length; i++) {
            email_string[i] = email_string[i].trim();
        }

        emailList.clear();
        emailList.addAll(Arrays.asList(email_string));
        emailsTA.setDisable(true);
        updatedemails.setText(emailList.toString());
    }


    public void ensureRegister(ActionEvent actionEvent) {

        String status = "pending";
        String inputPwd = passwordTF.getText();
        String inputConfirmedPwd = comfirmpasswordTF.getText();

        if(!checkPassWord(inputPwd, inputConfirmedPwd)) return;

        String sql = "INSERT INTO user\n" +
                "(`username`,\n" +
                "`firstname`,\n" +
                "`lastname`,\n" +
                "`status`,\n" +
                "`password`,\n" +
                "`usertype`)\n" +
                "VALUES\n" +
                "('"+usernameTF.getText()+"',\n'" +
                firstNameTF.getText()+"',\n'" +
                lastNameTF.getText()+"',\n'" +
                status+"',\n'" +
                passwordTF.getText()+"',\n'"+
                "user" +
                "')";
        System.out.println(sql);
        try {
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

            String emailAdd = "";

            for (int i = emailList.size(); i > 0; i--) {
                emailAdd = emailList.get(i - 1);
                String sqlForEmails = "INSERT INTO email\n" +
                        "(`email`,\n" +
                        "`username`)\n" +
                        "VALUES\n" +
                        "('" + emailAdd + "',\n'" +
                        usernameTF.getText() + "')";
                statement.executeUpdate(sqlForEmails);
            }
            statement.close();
            conn.commit();
            try{
                MyAlert.showAlert("Register succeed");
                Parent root = FXMLLoader.load(getClass().getResource("userlogin.fxml"));
                Stage stage = (Stage)registerBttn.getScene().getWindow();
                stage.setScene(new Scene(root));} catch (IOException e) {
                System.out.println(e);
            }
        } catch (SQLException e){
            System.out.println(e);
            MyAlert.showAlert(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        //statementForEmails.close();
    }

    public void ensureCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        //StaffEventDetail32 controller = fxmlLoader.getController();
        //controller.setLastFxml("staffviewschedule31.fxml");
        Stage stage = (Stage)registerBttn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public boolean checkPassWord(String a, String b) {
        if(!a.equals(b)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("password");
            alert.setContentText("password isn't the same as comfirmed password!");
            alert.showAndWait();
            return false;
        }
        else return true;
    }

    public void editEmail(ActionEvent actionEvent) {
        emailsTA.setDisable(false);
        //updatedemails.setText(emailList.toString());
    }
}
