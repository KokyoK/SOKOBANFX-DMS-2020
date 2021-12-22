package com.ae2dms.controller.TutorialController;

import com.ae2dms.Main;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @version: 1.0.0
 * @create: 2020-11-20 11:17
 * This class is the abstract class, which is will be extended by other tutorial class. <br/>
 **/
public abstract class TutorialController {

    /**
     * The global theme name (will determine the tutorial pic
     */
    String themeName = Main.themeName;

    /**
     * image view of tutorial pic
     */
    @FXML
    ImageView iv;

    /**
     * This method pre set the theme name of the tutorial controllers.<br/>
     * @param prevThemeNum
     */
    void preSetTheme(String prevThemeNum) {
        Image img = new Image("/image/"+themeName+"/tutorial/step"+prevThemeNum+".JPG");
        iv.setImage(img);
    }

}
