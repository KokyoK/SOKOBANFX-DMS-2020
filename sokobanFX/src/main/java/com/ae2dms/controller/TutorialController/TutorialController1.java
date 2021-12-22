package com.ae2dms.controller.TutorialController;

import com.ae2dms.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-20 11:24
 * This controller is to control the tutorial page 1
 **/
public class TutorialController1 extends TutorialController{

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
        super.preSetTheme("1");
    }


    /**
     * This method is used to go to next tutorial page
     * @throws IOException
     */
    @FXML
    public void goToNext() throws IOException {
        curStage =  (Stage)iv.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/tutorial-scene/Tutorial2-layout.fxml"));
        Parent root = loader.load();
        curStage.setScene(new Scene(root));
        TutorialController2 tutorialController2= loader.getController();
        tutorialController2.preSetStage(curStage);

    }

    /**
     * This method is for other class to pre set stage
     * @param curStage
     */
    void preSetStage(Stage curStage) {
        this.curStage = curStage;
    }

}
