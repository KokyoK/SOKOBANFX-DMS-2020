package com.ae2dms.controller.GameInitializer;

import com.ae2dms.model.GameEngines.GameEngine;
import com.ae2dms.model.GameEngines.GameEngineEntertain;
import com.ae2dms.model.GameEngines.GameEngineNormal;
import java.io.File;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @version: 1.0.0
 * @create: 2020-11-26 11:17
 * This class is used when user clicks reset game in menu bar when in main game page. <br/>
 *
 * It will destroy the previous game engine and generate a new game engine<br/>
 **/
public class ResetGameInitializer implements GameInitializer{
    /**
     * The game engine
     */
    GameEngine gameEngine;

    /**
     * the map set name in string format
     */
    String mapset;

    /**
     * Constructor of ResetGameInitializer
     *
     * Call the initializeGame() to initialize the gameEngine
     */
    public ResetGameInitializer(String mapset){
        this.mapset = mapset;
        String gameResourceLocation = getResourceLocation();
        File in = new File(gameResourceLocation);
        initializeGame(in);
    }

    /**
     * Get the game resource based on current game engine's mapset
     * @return the game map location
     */
    @Override
    public String getResourceLocation() {
        return "src/main/resources/level/"+mapset;
    }


    /**
     * Initialize the gameEngine with normal gameEngine
     * @param in the input file
     */
    @Override
    public void initializeGame(File in) {
        if (mapset.equals("Entertain.skb")){
            gameEngine = new GameEngineEntertain(in,true);
        }else{
            gameEngine = new GameEngineNormal(in,true);
        }

    }

    /**
     * For other class to get the initializer's map set name
     * @return the map set name
     */
    @Override
    public String getMapSet() {
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
