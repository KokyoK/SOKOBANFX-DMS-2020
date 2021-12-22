package com.ae2dms.controller.GameInitializer;

import com.ae2dms.Main;
import com.ae2dms.model.GameEngines.GameEngine;
import com.ae2dms.model.GameEngines.GameEngineEntertain;
import com.ae2dms.model.GameEngines.GameEngineNormal;
import javafx.stage.FileChooser;
import java.io.File;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-23 20:14
 * This class is used to allow user-chosen file to be loaded. <br/>
 *
 * It will be used in both mainGameController and StartPageController<br/>
 **/
public class CustomizeGameInitializer implements GameInitializer{
    /**
     * The game engine
     */
    GameEngine gameEngine;

    /**
     * the map set name in string format
     */
    private String mapSet;

    /**
     * For other class to get the initializer's map set name
     * @return the map set name
     */
    public String getMapSet() {
        return mapSet;
    }

    /**
     * Constructor of CustomizeGameInitializer
     *
     * Call the initializeGame() to initialize the gameEngine
     */
    public CustomizeGameInitializer(){
        String gameResourceLocation = getResourceLocation();
        File in = new File(gameResourceLocation);
        initializeGame(in);
    }

    /**
     * Get the game resource based on current game engine's mapset <br/>
     * Ask user to choose file manually in their local machine <br/>
     * @return the game map location
     */
    @Override
    public String getResourceLocation() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban save file", "*.skb"));
        File saveFile= fileChooser.showOpenDialog(Main.primaryStage);
        if (saveFile!= null) {
            mapSet = saveFile.getName();
        }
        return "src/main/resources/level/"+mapSet;
    }

    /**
     * For other class to get the initializer's game Engine
     * @return gameEngine
     */
    @Override
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     * Initialize the gameEngine with normal gameEngine
     * @param in the input file
     */
    @Override
    public void initializeGame(File in) {
        if(mapSet == null){
            return;
        }
        if(mapSet.equals("Entertain.skb")){
            gameEngine = new GameEngineEntertain(in,true);
        }else {
            gameEngine = new GameEngineNormal(in, true);
        }
    }
}
