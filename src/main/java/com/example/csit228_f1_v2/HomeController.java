package com.example.csit228_f1_v2;

import javafx.scene.control.*;
import javafx.stage.Stage;

public class HomeController {

    public ToggleButton tbNight;
    public ProgressIndicator piProgress;
    public Slider slSlider;
    public ProgressBar pbProgress;
    public Button logout;
    public Button changePassword;
    public Button tbChangeUsername;
    public TextField txtUsername;


    public void onSliderChange() {
        double val = slSlider.getValue();
        System.out.println(val);
        piProgress.setProgress(val/100);
        pbProgress.setProgress(val/100);
        if (val == 100) {
            System.exit(0);
        }
    }

    public void onNightModeClick() {
        if (tbNight.isSelected()) {
            tbNight.getParent().setStyle("-fx-background-color: BLACK");
            tbNight.setText("DAY");
        } else {
            tbNight.getParent().setStyle("-fx-background-color: WHITE");
            tbNight.setText("NIGHT");
        }
    }

    public void onLogout(){
        System.out.println("Current user to logout: " + HelloApplication.current_uid);
        HelloApplication.current_uid = -1;
        HelloApplication.loginPage(new Stage());

    }

    public void onChangePassword(){

    }

    public void onChangeUsername(){

    }
}
