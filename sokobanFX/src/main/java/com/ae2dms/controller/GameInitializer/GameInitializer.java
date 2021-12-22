package com.ae2dms.controller.GameInitializer;

import com.ae2dms.model.GameEngines.GameEngine;
import java.io.File;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @version: 1.0.0
 * @create: 2020-11-26 11:17
 * This interface is used for game initializer. <br/>
 **/
public interface GameInitializer {
    /**
     * @return map set location based on src
     */
    String getResourceLocation();

    /**
     * Gets game engine.
     * @return the game engine
     */
    GameEngine getGameEngine();

    /**
     * Initialize game.
     * @param in the in
     */
    void initializeGame(File in);

    /**
     * Gets map set.
     * @return the map set
     */
    String getMapSet();
}
