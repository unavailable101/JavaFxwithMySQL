package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Register {

    @FXML
    public TextField tfFirstName;
    @FXML
    public TextField tfLastName;
    @FXML
    public TextField tfEmail;
    @FXML
    public TextField tfPass;
    @FXML
    public Button btnSubmit;
    @FXML
    public Label lblKulang;
    @FXML
    public TextField tfUsername;

    public void onSubmit(ActionEvent actionEvent) {
        if (tfFirstName.getText().isBlank() ||
        tfLastName.getText().isBlank() ||
        tfEmail.getText().isBlank() ||
        tfPass.getText().isBlank() ||
        tfUsername.getText().isBlank() ){
            lblKulang.setText("*All fields are required");
            lblKulang.setVisible(true);
        } else {
            if ( !CreateTable.usersTable() ) CreateTable.createUser();
            if (InsertData.insertData(tfFirstName.getText(), tfLastName.getText(), tfUsername.getText(), tfEmail.getText(), tfPass.getText())) {
                HelloApplication.current_username = tfUsername.getText();
                try {
                    Parent p = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                    Scene s = new Scene(p);
                    HelloApplication.primaryStage.setScene(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                lblKulang.setText("*Username already exists");
                lblKulang.setVisible(true);
            }

        }
    }
}
