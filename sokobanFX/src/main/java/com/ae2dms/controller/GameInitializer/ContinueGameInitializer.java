package com.ae2dms.controller.GameInitializer;

import com.ae2dms.model.GameEngines.GameEngine;
import com.ae2dms.model.GameEngines.GameEngineNormal;
import java.io.File;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @version: 1.0.0
 * @create: 2020-11-23 19:41
 * This class is used when user clicks continue in the start page. <br/>
 *
 * It will generate a new game engine and automatically load the saved game last time. <br/>
 * The map set will be set as "SavedGame.skb"
 **/
public class ContinueGameInitializer implements GameInitializer{
    /**
     * The game engine
     */
    GameEngine gameEngine;

    /**
     * Constructor of ContinueGameInitializer
     *
     * Call the initializeGame() to initialize the gameEngine
     */
    public ContinueGameInitializer(){
        String gameResourceLocation = getResourceLocation();
        File in = new File(gameResourceLocation);
        initializeGame(in);
    }

    /**
     * Get the game resource
     * @return the game map location
     */
    @Override
    public String getResourceLocation() {
        return "src/main/resources/level/SavedGame.skb";
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
        return "SavedGame.skb";
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
