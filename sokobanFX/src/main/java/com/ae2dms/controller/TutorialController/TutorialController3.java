package com.ae2dms.controller.TutorialController;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-20 11:52
 * This controller is to control the tutorial page 3
 **/
public class TutorialController3 extends TutorialController{
    /**
     * image view of tutorial pic
     */
    @FXML
    ImageView iv;

    /**
     * Current Stage
     */
    @FXML
    Stage curStage;

    /**
     * The event handler of RIGHT keycode
     */
    EventHandler<KeyEvent> eventHandler;

    /**
     * Initialize the image path by calling super's method
     */
    @FXML
    private void initialize(){
        super.preSetTheme("3");
    }


    /**
     * This method is for other class to pre set stage and set event handler
     * @param curStage
     */
    @FXML
    void preSetStage(Stage curStage) {
        this.curStage = curStage;
        eventHandler = event -> {
            if (event.getCode() == KeyCode.DOWN) {
                try {
                    goToNext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        curStage.addEventFilter(KeyEvent.KEY_PRESSED,eventHandler);
    }

    /**
     * This method is used to go to next tutorial page
     * @throws IOException
     */
    @FXML
    private void goToNext() throws IOException {
        curStage.removeEventFilter(KeyEvent.KEY_PRESSED,eventHandler);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/tutorial-scene/Tutorial4-layout.fxml"));
        Parent root = loader.load();
        curStage.setScene(new Scene(root));
        TutorialController4 tutorialController4= loader.getController();
        tutorialController4.preSetStage(curStage);
    }

    /**
     * This method is used to go to previous tutorial page
     * @throws IOException
     */
    @FXML
    private void goToPrevious() throws IOException {
        curStage.removeEventFilter(KeyEvent.KEY_PRESSED,eventHandler);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/tutorial-scene/Tutorial2-layout.fxml"));
        Parent root = loader.load();
        curStage.setScene(new Scene(root));
        TutorialController2 tutorialController2= loader.getController();
        tutorialController2.preSetStage(curStage);
    }
}
