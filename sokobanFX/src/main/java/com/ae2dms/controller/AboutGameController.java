package com.ae2dms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/***
 * This controller show the about game page
 * @param 
 * @return 
 * @version 1.0.0
 */
public class AboutGameController {
    /**
     * The OK button
     */
    @FXML
    Button btn;

    /**
     * Close the stage
     */
    @FXML
    private void buttonClicked(){
        Stage stage = (Stage)btn.getScene().getWindow();
        stage.close();
    }
}
