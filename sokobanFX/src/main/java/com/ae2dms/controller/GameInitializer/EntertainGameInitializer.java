package com.ae2dms.controller.GameInitializer;

import com.ae2dms.model.GameEngines.GameEngine;
import com.ae2dms.model.GameEngines.GameEngineEntertain;
import java.io.File;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @version: 1.0.2
 * @create: 2020-11-25 11:17
 * This class is used for entertain game (two players). <br/>
 *
 * It will generate a new game engineEntertain.<br/>
 **/
public class EntertainGameInitializer implements GameInitializer{
    /**
     * The game engine
     */
    GameEngine gameEngine;

    /**
     * Constructor of EntertainGameInitializer
     *
     * Call the initializeGame() to initialize the gameEngine
     */
    public EntertainGameInitializer(){
        String gameResourceLocation = getResourceLocation();
        File in = new File(gameResourceLocation);
        initializeGame(in);
    }

    /**
     * Get the game resource for entertain game
     * @return the game map location
     */
    @Override
    public String getResourceLocation() {
        return "src/main/resources/level/Entertain.skb";
    }

    /**
     * Initialize the gameEngine with normal gameEngine
     * @param in the input file
     */
    @Override
    public void initializeGame(File in) {
        gameEngine = new GameEngineEntertain(in,true);
    }

    /**
     * For other class to get the initializer's map set name
     * @return the map set name
     */
    @Override
    public String getMapSet() {
        return "Entertain.skb";
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
