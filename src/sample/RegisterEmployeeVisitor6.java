package sample;

import connection.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pojo.UserInfo;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RegisterEmployeeVisitor6 {



    public TextField emailTF1;
    public TextField emailTF2;
    public TextField emailTF3;

    @FXML
    public Button registerBttn;

    @FXML
    public Button cancelBttn;

    @FXML
    public AnchorPane anchorpane;

    public TextField usernameTF;
    public TextField passwordTF;
    public TextField firstNameTF;
    public TextField lastNameTF;
    public TextField comfirmpasswordTF;

    public ComboBox typeBox;

    public ComboBox stateBox;

    public ArrayList<String> emailList;

    public int emailNumber;

    private static Connection conn;
    public TextField phonenumberTF;
    public TextField addressTF;
    public TextField cityTF;
    public TextField zipcodeTF;

    public static String lastFxml;
    public TextArea emailsTA;
    public Button updateEmailBttn;
    public Button editBttn;
    public Label updatedemails;
    public Label emailLael;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize() {
        emailList = new ArrayList<>();
        emailNumber = 0;

        ObservableList<String> typeOptions =
                FXCollections.observableArrayList(
                        "manager",
                        "staff"
                );

        ObservableList<String> stateOptions =
                FXCollections.observableArrayList(
                        "AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL",
                        "IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MO","MT","NE","NV",
                        "NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI","SC","SD","TD",
                        "TX","UT","VT","VA","WA","WV","WI","WY","Other");

        typeBox = new ComboBox(typeOptions);
        AnchorPane.setTopAnchor(typeBox, 135.0);
        AnchorPane.setLeftAnchor(typeBox, 415.0);
        anchorpane.getChildren().add(typeBox);

        stateBox = new ComboBox(stateOptions);
        AnchorPane.setTopAnchor(stateBox, 263.0);
        AnchorPane.setLeftAnchor(stateBox, 305.0);
        anchorpane.getChildren().add(stateBox);

        conn = ConnectionManager.getConn();

    }

    public void ensureUpdateEmail(ActionEvent actionEvent) {
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


    public void ensureRegister(ActionEvent actionEvent)  {
        String status = "pending";
        String inputPwd = passwordTF.getText();
        String inputConfirmedPwd = comfirmpasswordTF.getText();
        String userType = "";
        String state = "";

        if(!checkPassWord(inputPwd, inputConfirmedPwd)) return;
        userType = typeBox.getValue() + "";
        state = stateBox.getValue() + "";

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
                "employeevisitor" +
                "')";
        System.out.println(sql);
        try{
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);

//        for(int i=)
//        String sqlForEmails = "INSERT INTO user\n" +
//                "(`email`,\n" +
//                "`username`)\n" +
//                "VALUES\n" +
//                "('"+emailTF1.getText()+"',\n'" +
//                usernameTF.getText()+"')";
//        Statement statementForEmails = conn.createStatement();
//        statementForEmails.executeUpdate(sqlForEmails);

        //statement.close();

        String emailAdd = "";
        //Statement statementForEmails = conn.createStatement();

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

        //String sqlForGettingId = "SELECT * FROM user where " + ;
        Random r=new Random();
        int i1=r.nextInt(1000);
        String sqlForEmployee = "INSERT INTO employee\n" +
                "(`employeeID`,\n" +
                "`username`,\n" +
                "`phone`,\n" +
                "`address`,\n" +
                "`city`,\n" +
                "`state`,\n" +
                "`zipcode`,\n" +
                "`employeetype`)" +
                "VALUES\n" +
                "('"+"011044"+i1+"',\n'" +
                usernameTF.getText() + "',\n'" +
                phonenumberTF.getText()+"',\n'" +
                addressTF.getText()+"',\n'" +
                cityTF.getText() + "',\n'" +
                state + "',\n'"+
                zipcodeTF.getText() + "',\n'" +
                userType +
                "')";

        System.out.println(sqlForEmployee);

        statement.executeUpdate(sqlForEmployee);

        String sqlForVisitor = "INSERT INTO visitor\n" +
                "(`username`)\n" +
                "VALUES\n" +
                "('" + usernameTF.getText()+"')";

        statement.executeUpdate(sqlForVisitor);


        statement.close();
        }catch (SQLException e){
            MyAlert.showAlert(e.getMessage());
        }
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
