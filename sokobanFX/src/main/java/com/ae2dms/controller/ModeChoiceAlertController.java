package com.ae2dms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * This controller is to control the mode choice of the game.
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-25 09:44
 *
 **/
public class ModeChoiceAlertController {

    /**
     * The value of whether user has chosen the hell mode
     */
    private Boolean isHellMode = false;

    /**
     * The hint message. Here used to get current stage.
     */
    @FXML
    Label text;

    /**
     * @getter get the calur of isHellMode
     */
    public Boolean getHellMode() {
        return isHellMode;
    }

    /**
     * Set the normal mode and close the window
     */
    public void setNormalMode() {
        isHellMode = false;
        Stage stage =  (Stage) text.getScene().getWindow();
        stage.close();
    }

    /**
     * Set the hell mode and close the window
     */
    public void setHellMode() {
        isHellMode = true;
        Stage stage =  (Stage) text.getScene().getWindow();
        stage.close();
    }
}
