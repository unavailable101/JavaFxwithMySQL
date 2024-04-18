package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Optional;

public class HomeController extends Application {

    public ToggleButton tbNight;
    public ProgressIndicator piProgress;
    public Slider slSlider;
    public ProgressBar pbProgress;
    public Button logout;
    public Button changePassword;
    public Button tbChangeUsername;
    public Label txtUsername;
    public AnchorPane home;


//    public void onSliderChange() {
//        double val = slSlider.getValue();
//        System.out.println(val);
//        piProgress.setProgress(val/100);
//        pbProgress.setProgress(val/100);
//        if (val == 100) {
//            System.exit(0);
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {
//        txtUsername.setText(ReadData.get_username());
    }


    public void onNightModeClick() {
        if (tbNight.isSelected()) {
            tbNight.getParent().setStyle("-fx-background-color: WHITE");
            txtUsername.setTextFill(Color.BLACK);
            tbNight.setText("NIGHT");
        } else {
            tbNight.getParent().setStyle("-fx-background-color: BLACK");
            txtUsername.setTextFill(Color.WHITE);
            tbNight.setText("DAY");
        }
    }

    public void onLogout(){
        System.out.println("Current user to logout: " + HelloApplication.current_uid);
        HelloApplication.current_uid = -1;

//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setRoot(HelloApplication.primaryStage);
//        Parent root = fxmlLoader.load();
//
//        Scene scene = new Scene(root);
//        HelloApplication.primaryStage.setScene(scene);
        Stage currentStage = (Stage) home.getScene().getWindow();
        Scene currentScene = currentStage.getScene();
//        currentStage.setScene(null);
//        currentStage.setScene(HelloApplication.primaryStage.getScene());
//        currentStage.show();
        currentScene.getWindow().hide();
        HelloApplication.loginPage(HelloApplication.primaryStage);
    }

    public void onChangePassword(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Changing Password");
//        dialog.setHeaderText("Are you absolutely sure? This will delete all data present in the program.");
//        dialog.setGraphic(new Circle(15, Color.RED)); // Custom graphic
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        PasswordField old_pwd = new PasswordField();
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("Enter your old password to confirm:"), old_pwd);

        PasswordField new_pwd = new PasswordField();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("Enter your password:"), new_pwd);

        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                if (ReadData.getPassword(old_pwd.getText().toString())) {
                    return new_pwd.getText();
                } else {
                    System.out.println("Hacker ka noh?");
                }
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals(null)){
                UpdateData.updatePassword(HelloApplication.current_uid, result.get());
            }
//            System.out.println(result.get());
        }
    }

    public void onChangeUsername(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Changing Password");
//        dialog.setHeaderText("Are you absolutely sure? This will delete all data present in the program.");
//        dialog.setGraphic(new Circle(15, Color.RED)); // Custom graphic
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField newUsername = new TextField();
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("Enter your old password to confirm:"), newUsername);

        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return newUsername.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals(null)){
                UpdateData.updateUsername(HelloApplication.current_uid, result.get());
            }
//            System.out.println(result.get());
        }
    }
    public void onDeleteAccount(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delete Account");
//        dialog.setHeaderText("Are you absolutely sure? This will delete all data present in the program.");
//        dialog.setGraphic(new Circle(15, Color.RED)); // Custom graphic
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("Are you sure you want to delete your account? There is not turning back"));

        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return String.valueOf(HelloApplication.current_uid);
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals(null)){
                DeleteData.deleteAccount(HelloApplication.current_uid);

                Stage currentStage = (Stage) home.getScene().getWindow();
                Scene currentScene = currentStage.getScene();
                currentScene.getWindow().hide();
                HelloApplication.loginPage(HelloApplication.primaryStage);
            }
//            System.out.println(result.get());
        }
    }

}
