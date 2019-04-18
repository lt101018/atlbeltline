package sample;

import connection.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class registerEmployeeController {

    @FXML
    public Button addEmailBttn1;

    @FXML
    public Button addEmailBttn2;

    @FXML
    public Button addEmailBttn3;

    @FXML
    public Button removeEmailBttn1;

    @FXML
    public Button removeEmailBttn2;

    @FXML
    public Button removeEmailBttn3;

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

    public void initialize() {
        emailList = new ArrayList<>();
        emailNumber = 0;

        ObservableList<String> typeOptions =
                FXCollections.observableArrayList(
                        "Manager",
                        "User",
                        "Visitor"
                );

        ObservableList<String> stateOptions =
                FXCollections.observableArrayList(
                        "WA",
                        "GA",
                        "CA",
                        "NY"
                );

        typeBox = new ComboBox(typeOptions);
        AnchorPane.setTopAnchor(typeBox, 135.0);
        AnchorPane.setLeftAnchor(typeBox, 415.0);
        anchorpane.getChildren().add(typeBox);

        stateBox = new ComboBox(stateOptions);
        AnchorPane.setTopAnchor(stateBox, 263.0);
        AnchorPane.setLeftAnchor(stateBox, 305.0);
        anchorpane.getChildren().add(stateBox);

        conn = ConnectionManager.getConn();
        Button[] addButtonList = new Button[3];
        Button[] removeButtonList = new Button[3];

        addButtonList[0] = addEmailBttn1;
        addButtonList[1] = addEmailBttn2;
        addButtonList[2] = addEmailBttn3;
        removeButtonList[0] = removeEmailBttn1;
        removeButtonList[1] = removeEmailBttn2;
        removeButtonList[2] = removeEmailBttn3;
        removeEmailBttn1.setDisable(true);
        removeEmailBttn2.setDisable(true);
        removeEmailBttn3.setDisable(true);


        for (int i = 0; i < addButtonList.length; i++) {
            addButtonList[i].setOnAction(event -> checkIdForAdd((Button) event.getSource()));
            removeButtonList[i].setOnAction(event -> checkIdForRemove((Button) event.getSource()));
        }
    }

    private void checkIdForAdd(Button button) {
        
        if(button.getId().equals("addEmailBttn1")) {
            emailList.add(emailTF1.getText());
            emailTF1.setDisable(true);
            addEmailBttn1.setDisable(true);
            removeEmailBttn1.setDisable(false);
        } else if (button.getId().equals("addEmailBttn2")) {
            emailList.add(emailTF2.getText());
            emailTF2.setDisable(true);
            addEmailBttn2.setDisable(true);
            removeEmailBttn2.setDisable(false);
        } else {
            emailList.add(emailTF3.getText());
            emailTF3.setDisable(true);
            addEmailBttn3.setDisable(true);
            removeEmailBttn3.setDisable(false);
        }
        emailNumber++;
        System.out.println(emailList);
    }

    private void checkIdForRemove(Button button) {
        if(button.getId().equals("removeEmailBttn1")) {
            emailList.remove(emailTF1.getText());
            emailTF1.setText("");
            emailTF1.setDisable(false);
            addEmailBttn1.setDisable(false);
            removeEmailBttn1.setDisable(true);
        } else if (button.getId().equals("removeEmailBttn2")) {
            emailList.remove(emailTF2.getText());
            emailTF2.setText("");
            emailTF2.setDisable(false);
            addEmailBttn2.setDisable(false);
            removeEmailBttn2.setDisable(true);
        } else {
            emailList.remove(emailTF3.getText());
            emailTF3.setText("");
            emailTF3.setDisable(false);
            emailTF3.setDisable(false);
            addEmailBttn3.setDisable(false);
            removeEmailBttn3.setDisable(true);
        }

        if(emailNumber>0) emailNumber--;
        System.out.println(emailList);
    }

//    public void addEmailAddress(ActionEvent actionEvent) throws SQLException {
//
//        String emailAdd = "";
//        Statement statementForEmails = conn.createStatement();
//
//        for(int i=emailNumber; i>0; i--) {
//            emailAdd = emailTF1.getText();
//            String sqlForEmails = "INSERT INTO user\n" +
//                    "(`email`,\n" +
//                    "`username`)\n" +
//                    "VALUES\n" +
//                    "('"+emailAdd+"',\n'" +
//                    usernameTF.getText()+"')";
//
//            statementForEmails.executeUpdate(sqlForEmails);
//        }
//
//        statementForEmails.close();
//
//    }


    public void ensureRegister(ActionEvent actionEvent) throws SQLException {
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
                "`password`)\n" +
                "VALUES\n" +
                "('"+usernameTF.getText()+"',\n'" +
                firstNameTF.getText()+"',\n'" +
                lastNameTF.getText()+"',\n'" +
                status+"',\n'" +
                passwordTF.getText()+"')";

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

        statement.close();

        String emailAdd = "";
        Statement statementForEmails = conn.createStatement();

        for(int i=emailNumber; i>0; i--) {
            emailAdd = emailTF1.getText();
            String sqlForEmails = "INSERT INTO user\n" +
                    "(`email`,\n" +
                    "`username`)\n" +
                    "VALUES\n" +
                    "('"+emailAdd+"',\n'" +
                    usernameTF.getText()+"')";

            statementForEmails.executeUpdate(sqlForEmails);
        }

        statementForEmails.close();
        //statementForEmails.close();
    }

    public void ensureCancel(ActionEvent actionEvent) throws IOException {

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
}