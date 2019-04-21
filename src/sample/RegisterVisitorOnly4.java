package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class RegisterVisitorOnly4 {

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

    public static String lastFxml;
    public TextArea emailsTA;
    public Button updateEmailBttn;
    public Button editBttn;
    public Label emailLael;
    public Label updatedemails;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    private static Connection conn;

    public void initialize() {
        emailList = new ArrayList<>();
        emailNumber = 0;
        System.out.println("Controller initializing for visitor!!");
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

    public void ensureRegister(ActionEvent actionEvent) throws SQLException {
        String status = "pending";
        String inputPwd = passwordTF.getText();
        String inputConfirmedPwd = comfirmpasswordTF.getText();

        if(!checkPassWord(inputPwd, inputConfirmedPwd)) return;

        Statement statement = conn.createStatement();
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
                "visitor" +
                "')";

        statement.executeUpdate(sql);

        String sqlForVisitor = "INSERT INTO visitor\n" +
        "(`username`)\n" +
        "VALUES\n" +
        "('" + usernameTF.getText()+"')";

        statement.executeUpdate(sqlForVisitor);

        String emailAdd = "";

        for(int i=emailList.size(); i>0; i--) {
            emailAdd = emailList.get(i-1);
            String sqlForEmails = "INSERT INTO email\n" +
                    "(`email`,\n" +
                    "`username`)\n" +
                    "VALUES\n" +
                    "('"+emailAdd+"',\n'" +
                    usernameTF.getText()+"')";
            statement.executeUpdate(sqlForEmails);
        }

        statement.close();
    }

    public void ensureCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)registerBttn.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    public boolean checkPassWord(String a, String b) {
        if(!a.equals(b)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Look, a Warning Dialog");
            alert.setContentText("Careful with the next step!");
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
