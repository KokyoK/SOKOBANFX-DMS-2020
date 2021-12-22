package com.ae2dms.controller;

import com.ae2dms.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/***
 * This controller control the difficulty choice
 * @author: Yuting He
 * @version: 1.0.0
 */
public class DifficultyChoiceController {
    /**
     * Group of difficulty choice
     */
    @FXML
    private ToggleGroup difficulty;

    /**
     * Simple radio button
     */
    @FXML
    private RadioButton radioSimple;

    /**
     * Medium radio button
     */
    @FXML
    private RadioButton radioMedium;

    /**
     * Hard radio button
     */
    @FXML
    private RadioButton radioHard;

    /**
     * Simple image view
     */
    @FXML
    private ImageView simpleIv;

    /**
     * Medium image view
     */
    @FXML
    private ImageView mediumIv;

    /**
     * Hard image view
     */
    @FXML
    private ImageView hardIv;

    /**
     * the ok button
     */
    @FXML
    private Button okBtn;

    /**
     * Initialize the difficulty choice based on theme<br/>
     * Set on listening the choice
     */
    @FXML
    public void initialize() {
        preSetTheme();
        preSetButtons();
        difficulty.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> arg0, Toggle old_toggle, Toggle new_toggle) {
                String difficulty = ((RadioButton)new_toggle).getText();
                Main.difficulty = difficulty;
            }
        });
    }

    /**
     * Preset the theme of difficulty choice
     * Especially set the images
     */
    @FXML
    private void preSetTheme() {
        String themeName = Main.themeName;
        String imgPath = "/image/";
        Image simpleImg  = new Image(imgPath + themeName + "/difficulty/simple.png");
        Image mediumImg  = new Image(imgPath + themeName + "/difficulty/medium.png");
        Image hardImg  = new Image(imgPath + themeName + "/difficulty/hard.png");

        simpleIv.setImage(simpleImg);
        mediumIv.setImage(mediumImg);
        hardIv.setImage(hardImg);
    }


    /**
     * Pre set the buttons based on last choice
     * If first time open the page, should set to simple
     */
    @FXML
    private void preSetButtons(){
        String difficultyName = Main.difficulty;
        System.out.println(difficultyName);
        if (difficultyName.equals("simple")){
            radioSimple.setSelected(true);
        }else if(difficultyName.equals("medium")){
            radioMedium.setSelected(true);
        }else{
            radioHard.setSelected(true);
        }
    }

    /**
     * Handle window close
     */
    @FXML
    private void closeWindow(){
        Stage stage = (Stage)okBtn.getScene().getWindow();
        stage.close();
    }

}
