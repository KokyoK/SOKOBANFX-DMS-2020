package com.ae2dms.model;

import com.ae2dms.Main;
import com.ae2dms.model.GameEngines.GameEngine;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * This class set shape and color for different object
 * @Author: Yuting He
 * @Version: 1.0
 */
public class GraphicObject extends ImageView {

    /**
     * The current theme name
     */
    private String themeName = Main.themeName;

    /**
     * The image view of current object
     */
    public javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView();

    /**
     * @setter set the theme name
     * @param themeName
     */
    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    /**
     * This constructor constructed graphic object
     * @param obj
     */
    public GraphicObject(GameObject obj) {
        GameImage gameImage = GameImage.getGameImage(themeName);
        switch (obj) {

            case WALL:
                iv.setImage(gameImage.wallImg);
                break;

            case CRATE:
                iv.setImage(gameImage.crateImg);
                break;

            case DIAMOND:
                iv.setImage(gameImage.diamondImg);

                if (GameEngine.isDebugActive()) {
                    FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
                    ft.setFromValue(1.0);
                    ft.setToValue(0.2);
                    ft.setCycleCount(Timeline.INDEFINITE);
                    ft.setAutoReverse(true);
                    ft.play();
                }

                break;

            case KEEPER:
                iv.setImage(gameImage.keeperImg);
                break;

            case FRIEND:
                // color = Color.RED;
                iv.setImage(gameImage.friendImg);
                break;

            case FLOOR:
                // color = Color.WHITE;
                iv.setImage(gameImage.floorImg);
                break;

            case CRATE_ON_DIAMOND:
                // color = Color.DARKCYAN;
                iv.setImage(gameImage.crateOnDiamondImg);
                break;

            case KEEPER_ON_DIAMOND:
                // color = Color.BLACK;
                iv.setImage(gameImage.keeperOnDiamondImg);
                break;
            case FRIEND_ON_DIAMOND:
                // color = Color.RED;
                iv.setImage(gameImage.friendOnDiamondImg);
                break;

            default:
                String message = "Error in Level constructor. Object not recognized.";
                GameEngine.logger.severe(message);
                throw new AssertionError(message);
        }

        iv.setFitWidth(50);
        iv.setFitHeight(50);

    }

    /**
     * Set the size of imageView (for grid to adapt to size of its bounder)
     * @param objSize
     */
    public void setIv(double objSize){
        iv.setFitHeight(objSize);
        iv.setFitWidth(objSize);
    }

}

