package com.ae2dms.controller.GameInitializer;

import com.ae2dms.Main;
import com.ae2dms.model.GameEngines.GameEngine;
import com.ae2dms.model.GameEngines.GameEngineNormal;
import java.io.File;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @version: 1.0.0
 * @create: 2020-11-23 19:39
 * This class is used when user clicks new game in the start page. <br/>
 *
 * It will generate a new game engine and automatically load the game depends on the difficulty choice.<br/>
 **/
public class NewGameInitializer implements GameInitializer {
    /**
     * The game engine
     */
    GameEngine gameEngine;

    /**
     * the map set name in string format
     */
    String mapset = "";

    /**
     * Constructor of NewGameInitializer
     *
     * Call the initializeGame() to initialize the gameEngine
     */
    public NewGameInitializer(){
        String gameResourceLocation = getResourceLocation();
        File in = new File(gameResourceLocation);
        initializeGame(in);
    }

    /**
     * Get the game resource based on difficulty choice
     * @return the game map location
     */
    @Override
    public String getResourceLocation() {
        if (Main.difficulty.equals("simple")){
            mapset = "Simple.skb";
            return "src/main/resources/level/Simple.skb";
        }else if(Main.difficulty.equals("medium")){
            mapset = "Medium.skb";
            return "src/main/resources/level/Medium.skb";
        }else{
            mapset = "Hard.skb";
            return "src/main/resources/level/Hard.skb";
        }

    }

    /**
     * Initialize the gameEngine with normal gameEngine
     * @param in the input file
     */
    @Override
    public void initializeGame(File in) {
        gameEngine = new GameEngineNormal(in,true);
    }

    /**
     * For other class to get the initializer's map set name
     * @return the map set name
     */
    @Override
    public String getMapSet() {
        System.out.println("in new initializer:" + mapset);
        return mapset;
    }

    /**
     * For other class to get the initializer's game Engine
     * @return gameEngine
     */
    @Override
    public GameEngine getGameEngine() {
        return gameEngine;
    }

}
