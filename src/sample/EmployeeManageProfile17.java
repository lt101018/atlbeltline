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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import pojo.UserInfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeManageProfile17 {

    public String firstName;
    public String lastName;
    public static String userName;
    public String siteName;
    public String employeeID;
    public static ArrayList<String> emailList;
    public Label address;
    public String phone;
    public boolean isVisitor;
    public CheckBox isVisitorCheckBox;
    public TextArea emailsTA;
    public Button updateBttn;
    public Label username;
    public Label employeeid;
    public TextField phoneTF;
    public Label sitename;
    public Button ensureEmailBttn;
    public Button editEmailBttn;
    public Label updatedemails;
    private int emailNumber;
    public TextField firstNameTF;
    public TextField lastNameTF;
    private String usertype;

    public ListView<String> emailListView;
    public static ObservableList<String> studentObservableList;

    private Connection conn;

    public static String lastFxml;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

//    public void setAll(String username) {
//        this.userName = username;
//        System.out.println("Currently, in screen17 the username is: ");
//    }

    public void initialize() throws SQLException {
        conn = ConnectionManager.getConn();
        userName = UserInfo.username;

        /*
         * sqLl for email
         * */
        String sqlForEmail = "SELECT * FROM email where username=" +
                "'" + userName + "'";

        Statement statement = conn.createStatement();
        emailList = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery(sqlForEmail);
        while (resultSet.next()) {
            String read_email = resultSet.getString("email");
            emailList.add(read_email);
        }

        emailNumber = emailList.size();

        username.setText(userName);
        String emailListString = emailList.toString();
        if (emailNumber != 0) {
            emailsTA.setText(emailListString.substring(1, emailListString.lastIndexOf("]")));
        } else {
            emailsTA.setText("");
        }
        updatedemails.setText(emailListString);

        /*
         * sql for user part
         * */
        String sqlForUser = "SELECT * FROM user where username=" +
                "'" + UserInfo.username + "'";
        resultSet = statement.executeQuery(sqlForUser);

        while (resultSet.next()) {
            String read_firstname = resultSet.getString("firstname");
            String read_lastname = resultSet.getString("lastname");
            firstNameTF.setText(read_firstname);
            lastNameTF.setText(read_lastname);
        }

        /*
         * sql for user employee
         * */

        String sqlForEmployee = "SELECT * FROM employee where username=" +
                "'" + UserInfo.username + "'";

        resultSet = statement.executeQuery(sqlForEmployee);
        while (resultSet.next()) {
            String read_employeeid = resultSet.getString("employeeID");
            String read_address = resultSet.getString("address");
            String read_phone = resultSet.getString("phone");
            employeeid.setText(read_employeeid);
            address.setText(read_address);
            phoneTF.setText(read_phone);
        }

        /*
         * sql for user site
         * */

        String sqlForSite = "SELECT * FROM site where managerusername=" +
                "'" + UserInfo.username + "'";
        resultSet = statement.executeQuery(sqlForSite);
        while (resultSet.next()) {
            String read_site = resultSet.getString("name");
            sitename.setText(read_site);
        }

        usertype = UserInfo.usertype;
        System.out.println("The usertype is " + usertype);
        if(usertype.equals(new String("employee"))) {
            isVisitorCheckBox.setSelected(false);
        }
        if(usertype.equals(new String("employeevisitor"))) {
            isVisitorCheckBox.setSelected(true);
        }

        statement.close();
    }

    public void updateAll() throws SQLException {
        String newFirstName = firstNameTF.getText();
        String newLastName = lastNameTF.getText();
        String newPhone = phoneTF.getText();
        String username = UserInfo.username;
        Statement statement = conn.createStatement();

        String updateUser = "update user\n" +
                "set firstname = " +
                "'" + newFirstName + "', " +
                "lastname = " + "'"+ newLastName + "'\n" +
                "where username = " + "'" + username + "'";
        statement.executeUpdate(updateUser);

        String updatePhone = "update employee\n" +
                "set phone = " +
                "'" + newPhone + "'\n" +
                "where username = " + "'" + username + "'";
        statement.executeUpdate(updatePhone);
        statement.close();
    }


    public void ensureUpdateEmail(ActionEvent actionEvent) throws SQLException{
        Statement statement = conn.createStatement();
        for(int i=0; i<emailList.size(); i++) {
            String sqlForDelete = "DELETE FROM email\n" +
                    "WHERE email ='"+ emailList.get(i) +"'";
            statement.executeUpdate(sqlForDelete);
        }
        String read_wroteemails = "";
        read_wroteemails = emailsTA.getText();
        String[] email_string = read_wroteemails.split(",");
        emailList.clear();
        System.out.println("After being cleaned, the emailList is: " + emailList);
        emailList.addAll(Arrays.asList(email_string));
        System.out.println("After being added, the emailList is: " + emailList);
        String emailAdd = "";

        emailNumber = emailList.size();
        for(int i=emailNumber; i>0; i--) {
            emailAdd = emailList.get(i-1);
            String sqlForEmails = "INSERT INTO email\n" +
                    "(`email`,\n" +
                    "`username`)\n" +
                    "VALUES\n" +
                    "('"+emailAdd+"',\n'" +
                    UserInfo.username+"')";
            statement.executeUpdate(sqlForEmails);
        }
        emailsTA.setDisable(true);
        updatedemails.setText(emailList.toString());
    }

    public void ensureUpdate() throws SQLException {
        Statement statement = conn.createStatement();
        System.out.println("I am here! The usertype is: " + usertype);
        if(usertype.equals(new String("employee"))) {
            // Query
            System.out.println("I am a employee");
            updateAll();
            if(isVisitorCheckBox.isSelected()) {
                String sqlForInsertUser = "";
                sqlForInsertUser = "insert into visitor\n" +
                        "(`username`)\n" +
                        "VALUES\n" +
                        "('" + UserInfo.username + "')";
                statement.executeUpdate(sqlForInsertUser);

                String updateUser = "update user\n" +
                        "set usertype = " +
                        "'" + "employeevisitor" + "'\n" +
                        "where username = " + "'" + UserInfo.username + "'";
                System.out.println(updateUser);
                statement.executeUpdate(updateUser);
                this.setLastFxml("userlogin.fxml");
            }
        } else if(usertype.equals(new String("employeevisitor"))) {
            System.out.println("I am a employeevisitor");
            updateAll();
            if(!isVisitorCheckBox.isSelected()) {
                String sqlForDeleteVisitor = "DELETE FROM visitor\n" +
                        "WHERE username ='"+ UserInfo.username +"'";
                statement.executeUpdate(sqlForDeleteVisitor);

                String updateUser = "update user\n" +
                        "set usertype = " +
                        "'" + "employee" + "'\n" +
                        "where username = " + "'" + UserInfo.username + "'";
                System.out.println(updateUser);
                statement.executeUpdate(updateUser);
                this.setLastFxml("userlogin.fxml");
            }
            //System.out.println("!!Here!!");
            // Delete all visit history of the employee
            // Update the user type! and back to the screen 12!
            statement.close();
        }
    }

    public void ensureCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)updateBttn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void editEmail(ActionEvent actionEvent) {
        emailsTA.setDisable(false);
        //updatedemails.setText(emailList.toString());
    }

    public void ensureEmail(ActionEvent actionEvent) {

    }
}
