package com.ae2dms.controller.TutorialController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-20 11:54
 * This controller is to control the tutorial page 4
 **/
public class TutorialController4 extends TutorialController{

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
     * Initialize the image path by calling super's method
     */
    @FXML
    private void initialize(){
        preSetTheme();
    }

    /**
     * This method pre set the theme name of the tutorial controllers.<br/>
     */
    private void preSetTheme() {
        super.preSetTheme("4");
    }

    /**
     * This method is used to go to previous tutorial page
     * @throws IOException
     */
    @FXML
    private void goToPrevious() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/tutorial-scene/Tutorial3-layout.fxml"));
        Parent root = loader.load();
        curStage.setScene(new Scene(root));
        TutorialController3 tutorialController3 = loader.getController();
        tutorialController3.preSetStage(curStage);
    }

    /**
     * This method is for other class to pre set stage and set event handler
     * @param curStage
     */
    void preSetStage(Stage curStage) {
        this.curStage = curStage;
    }

    /**
     * Close the stage
     * @param actionEvent
     */
    public void finish(ActionEvent actionEvent) {
      curStage =  (Stage)iv.getScene().getWindow();
      curStage.close();
    }
}
