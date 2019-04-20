package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;

public class EmailListViewCell extends ListCell<String> {

    @FXML
    public Button addBttn;

    @FXML
    public Button removeBttn;

    @FXML
    public TextField emailAddressTF;

    @FXML
    public AnchorPane anchorpane1;

    private FXMLLoader mLLoader;

    ArrayList<String> employeeEmailList = EmployeeManageProfile17.emailList;

    @Override
    protected void updateItem(String email, boolean empty) {
        super.updateItem(email, empty);

        if(empty || email == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/sample/ListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            emailAddressTF.setText(String.valueOf(email));
            addBttn.setOnAction(event -> funForAdd((Button) event.getSource()));
            removeBttn.setOnAction(event -> funForRemove((Button) event.getSource()));
            setText(null);
            setGraphic(anchorpane1);
        }
    }

    public void funForAdd(Button button) {
        //System.out.println(getItem());
        //System.out.println(emailAddressTF.getId());
        //emailAddressTF.setDisable(true);
        //button.setDisable(true);
        employeeEmailList.add(emailAddressTF.getText());
        //System.out.println(employeeEmailList);
        EmployeeManageProfile17.studentObservableList.add("");
        //getListView().getItems().add(EmployeeManageProfile17.emailListView.getItems().size()-1, "");
        System.out.println(employeeEmailList);
    }



    public void funForRemove(Button button) {
        EmployeeManageProfile17.studentObservableList.remove(getItem());
        employeeEmailList.remove(emailAddressTF.getText());
        System.out.println(employeeEmailList);
    }


}
