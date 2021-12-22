package com.ae2dms.model;

import com.ae2dms.Main;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 * @program: sokobanFX
 * @description: This class is used to load image (avoid loading image everytime)
 *               Designed with singleton pattern (lazy Double Check Singleton)
 * @author: Yuting He
 * @create: 2020-11-14 18:16
 **/
public class GameImage {
    /**
     * The current theme name
     */
    static String themeName = Main.themeName;

    /**
     * The imagr path
     */
    static String imgPath = "/image/";

    /**
     * The instance of the class (singleton)
     */
    private static GameImage instance=null;

    /**
     * Game images
     */
    static Image keeperImg;
    static Image friendImg;

    static Image wallImg;
    static Image shownWallImg;
    static Image hiddenWallImg;

    static Image crateImg;
    static Image floorImg;
    static Image diamondImg;
    static Image keeperOnDiamondImg;
    static Image friendOnDiamondImg;
    static Image crateOnDiamondImg;

    static Image upImg;
    static Image downImg;
    static Image leftImg;
    static Image rightImg;

    static Image upImgFriend;
    static Image downImgFriend;
    static Image leftImgFriend;
    static Image rightImgFriend;


    public GameImage(String themeName){
        this.themeName = themeName;
        getImageResource();
    }

    public static void setThemeName(String themeName) {
        GameImage.themeName = themeName;
    }

    /**
     * Read from resource to get game image
     */
    private void getImageResource()
    {
        crateImg = new Image(imgPath + themeName + "/obj/crate.JPG");
        floorImg  = new Image(imgPath + themeName + "/obj/floor.JPG");
        hiddenWallImg = new Image(imgPath + themeName + "/obj/floor.JPG");
        shownWallImg = new Image(imgPath + themeName + "/obj/wall.JPG");
        wallImg = new Image(imgPath + themeName + "/obj/wall.JPG");


        keeperImg = new Image(imgPath + themeName + "/obj/down.JPG");
        friendImg = new Image(imgPath + themeName + "/obj/downFriend.JPG");

        diamondImg = new Image(imgPath + themeName + "/obj/diamond.JPG");
        keeperOnDiamondImg = new Image(imgPath + themeName + "/obj/keeper_on_diamond.JPG");
        friendOnDiamondImg = new Image(imgPath + themeName + "/obj/friend_on_diamond.JPG");
        crateOnDiamondImg = new Image(imgPath + themeName + "/obj/crate_on_diamond.JPG");

        upImg = new Image(imgPath + themeName + "/obj/up.JPG");
        downImg = new Image(imgPath + themeName + "/obj/down.JPG");
        leftImg = new Image(imgPath + themeName + "/obj/left.JPG");
        rightImg = new Image(imgPath + themeName + "/obj/right.JPG");

        upImgFriend = new Image(imgPath + themeName + "/obj/upFriend.JPG");
        downImgFriend = new Image(imgPath + themeName + "/obj/downFriend.JPG");
        leftImgFriend = new Image(imgPath + themeName + "/obj/leftFriend.JPG");
        rightImgFriend = new Image(imgPath + themeName + "/obj/rightFriend.JPG");

    }


    /**
     * Get game image from the instance
     * @param objName
     * @return
     */
    public static GameImage getGameImage(String objName){
        if (instance == null) {
            synchronized (GameImage.class) {
                if (instance == null) {
                    instance = new GameImage(objName);
                }
            }
        }
        return instance;
    }

    /**
     * This method hides the wall after player crashes into the wall
     */
    public static void hideWallImg(){
        wallImg = hiddenWallImg;
    }

    /**
     * This method shows the wall image when the player crashed into the wall
     */
    public static void showWallImg(){
        wallImg = shownWallImg;
    }

    /**
     * Set the keeper direction and change the image when keeper moves
     * @param code
     */
    public static void setDirection(KeyCode code){
        switch (code)
        {
            case UP:
                keeperImg = upImg;
                break;
            case W:
                friendImg = upImgFriend;
                break;

            case DOWN:
               keeperImg = downImg;
                break;
            case S:
                friendImg = downImgFriend;
                break;

            case LEFT:
                keeperImg = leftImg;
                break;
            case A:
                friendImg = leftImgFriend;
                break;

            case RIGHT:
                keeperImg = rightImg;
                break;
            case D:
                friendImg = rightImgFriend;
                break;

            default:
                return;
        }
    }


}
