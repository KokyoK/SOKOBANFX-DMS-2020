package com.ae2dms.controller;

import com.ae2dms.Main;
import com.ae2dms.model.GameImage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * This controller allow user to chose theme
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-10 20:30
 **/
public class ThemeChoiceController {

    /**
     * The group of theme choice
     */
    @FXML
    private ToggleGroup theme;

    /**
     * The radiobutton of theme-crayon
     */
    @FXML
    private RadioButton radioCrayon;

    /**
     * The radio button of theme-simple
     */
    @FXML
    private RadioButton radioSimple;

    /**
     * The ok button
     */
    @FXML
    private Button okBtn;

    /**
     * Initialize of the stage.
     * set a observer to observe change of the toggle group.
     * Pre set button based on current theme.
     */
    @FXML
    public void initialize() {
        preSetButtons();

        theme.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> arg0, Toggle old_toggle, Toggle new_toggle) {
                String themeName = ((RadioButton)new_toggle).getText();
                Main.themeName = themeName;
                GameImage gameImage = GameImage.getGameImage(Main.themeName);
                gameImage.setThemeName(themeName);
            }
        });
    }

    /**
     * This method get the current theme from main.
     * Set the current theme selected.
     */
    private void preSetButtons(){
        String themeName = Main.themeName;
        if (themeName.equals("theme-crayon")){
            radioCrayon.setSelected(true);
        }else{
            radioSimple.setSelected(true);
        }
    }

    /**
     * This method close the current stage.
     */
    @FXML
    private void closeWindow(){
        Stage stage = (Stage)okBtn.getScene().getWindow();
        stage.close();
    }


}
