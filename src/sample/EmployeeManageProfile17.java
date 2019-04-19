package sample;

import connection.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.sql.Connection;
import java.util.ArrayList;

public class EmployeeManageController {

    public String lastFxml;

    public String firstName;
    public String lastName;
    public String userName;
    public String siteName;
    public String employeeID;
    public static ArrayList<String> emailList;
    public String address;
    public String phone;
    public boolean isVisitor;
    public CheckBox isVisitorCheckBox;
    public AnchorPane anchorpane;
    public Label Address;
    private int emailNumber;

    public Label Sitename;
    public Label Username;
    public Label Employeeid;
    public Label Phone;
    public TextField firstNameTF;
    public TextField lastNameTF;

    public Button addEmailBttn1;
    public Button addEmailBttn2;
    public Button addEmailBttn3;
    public Button removeEmailBttn1;
    public Button removeEmailBttn2;
    public Button removeEmailBttn3;
    public TextField emailTF1;
    public TextField emailTF2;
    public TextField emailTF3;

    public ListView<String> emailListView;
    public static ObservableList<String> studentObservableList;

    private Connection conn;

    public void initialize() {
        conn = ConnectionManager.getConn();
        emailList = new ArrayList<>();
        studentObservableList = FXCollections.observableArrayList();

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

        emailNumber = emailList.size();

        for (int i = 0; i < addButtonList.length; i++) {
            addButtonList[i].setOnAction(event -> checkIdForAdd((Button) event.getSource()));
            removeButtonList[i].setOnAction(event -> checkIdForRemove((Button) event.getSource()));
        }
        studentObservableList.addAll("email1","email2","email3");
        emailListView.setItems(studentObservableList);
        emailListView.setCellFactory(studentListView -> new EmailListViewCell());

        emailListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> emailListView) {
                return new EmailListViewCell();
            }
        });

        /*
        * Below is the original version of ListView.
        * */
//        ObservableList<String> list = FXCollections.observableArrayList(
//                "Item 1");
//        lv = new ListView<>(list);
//        lv.setCellFactory(param -> new XCell());
//        AnchorPane.setTopAnchor(lv, 263.0);
//        AnchorPane.setLeftAnchor(lv, 305.0);
//        anchorpane.getChildren().add(lv);
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

    public void getAndSetText() {
        // Get the userName and other important information
       // .setText("");

        firstNameTF.setText(firstName);
        lastNameTF.setText(lastName);
        Sitename.setText(siteName);
        Username.setText(userName);
        Employeeid.setText(employeeID);
        Phone.setText(phone);
        Address.setText(address);
    }

    public void ensureUpdate() {
        if(isVisitorCheckBox.isSelected()) {
            // Query
        } else {
            // Delete all visit history of the employee

            // Update the user type! and back to the screen 12!


        }
    }

    public void setLastFxml(String lastFxml) {
        //back to the 13 screen;
        this.lastFxml = lastFxml;
    }

    public void ensureRegister(ActionEvent actionEvent) {
    }

    public void ensureCancel(ActionEvent actionEvent) {
    }


//    static class XCell extends ListCell<String> {
//        HBox hbox = new HBox();
//        Label label = new Label("");
//        TextField emailTF = new TextField();
//        Pane pane = new Pane();
//        Button removeButton = new Button("Remove");
//        Button addButton = new Button("Add");
//
//        public XCell() {
//            super();
//            hbox.getChildren().addAll(emailTF, pane, addButton, removeButton);
//            HBox.setHgrow(pane, Priority.ALWAYS);
//            removeButton.setOnAction(event -> getListView().getItems().remove(getItem()));
//            addButton.setOnAction(event -> addButtonAction((Button) event.getSource()));
//            System.out.println("In Constructor!");
//        }
//
//        public void addButtonAction(Button sourceButton) {
//            String emailtext = emailTF.getText();
//            if(!emailtext.equals("")) {
//                getListView().getItems().add(lv.getItems().size()-1, emailTF.getText());
//                emailTF.setDisable(true);
//                sourceButton.setDisable(false);
//                emailTF = new TextField();
//            } else {
//
//            }
//            System.out.println("In addButtonAction() Function, now the emailtext is " + emailtext);
//        }
//
//        @Override
//        public void updateItem(String item, boolean empty) {
//            // don't omit this!!!
//            super.updateItem(item, empty);
//
//            if (empty) {
//                setGraphic(null);
//            } else {
//                // update controller and ui as necessary
//                    if (item != null && !empty) {
//                    emailTF.setText(item);
//                    this.setGraphic(hbox);
//                    emailTF = new TextField();
//                }
//            }
//        }
//    }
}
