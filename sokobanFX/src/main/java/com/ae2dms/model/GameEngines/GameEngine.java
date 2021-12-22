package com.ae2dms.model.GameEngines;

import com.ae2dms.model.GameLogger;
import com.ae2dms.model.Level;
import com.ae2dms.model.LevelMemo;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class is an abstract gameEngine, with method {@code handleKey()}and {@code Move()} not implemented.
 * It changes the object grid and diamond grid when keeper moved.
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-22 14:15
 **/
public abstract class GameEngine {
    /**
     * The game name.
     */
    public static final String GAME_NAME = "SokobanFX";

    /**
     * The game logger for debug.
     */
    public static GameLogger logger;

    /**
     * The movesCount of current map set
     */
    private int movesCount = 0;

    /**
     * The map set name
     */
    public String mapSetName;

    /**
     * The debug boolean
     */
    private static boolean debug = false;

    /**
     * The current level.
     */
    public Level currentLevel;

    /**
     * The levels list
     */
    public List<Level> levels;

    /**
     * The boolean value represents whether the game is completed
     */
    private boolean gameComplete = false;

    /**
     * The level memory (for undo)
     */
    public LevelMemo levelMemo  = LevelMemo.getLevelMemo();

    /**
     * The boolea value represents whether the current level has completed
     */
    public Boolean isCurLevelComplete = false;

    /**
     * The boolean value represents whether the keeper crash onto the wall
     */
    private Boolean isKeeperToWall;

    /**
     * The boolean value represents whther it is the hell mode
     */
    private Boolean isHellMode = false;

    /**
     * @getter get isKeeperToWall
     * @return isKeeperToWall
     */
    public Boolean getKeeperToWall() {
        return isKeeperToWall;
    }

    /**
     * @setter set isKeeperToWall
     * @param keeperToWall
     */
    public void setKeeperToWall(Boolean keeperToWall) {
        isKeeperToWall = keeperToWall;
    }

    /**
     * @setter set isHellMode
     * @param hellMode
     */
    public void setHellMode(Boolean hellMode) {
        isHellMode = hellMode;
    }

    /**
     * @getter get is HellMode
     * @return
     */
    public Boolean getHellMode() {
        return isHellMode;
    }

    /**
     * @getter get movesCount
     * @return
     */
    public int getMovesCount() {
        return movesCount;
    }

    /**
     * @setter set current level
     * @param idx
     */
    public void setCurrentLevel(int idx){
        currentLevel = levels.get(idx-1);
    }

    /**
     * @getter get current level
     * @return currentLevel
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @getter get levels
     * @return levels
     */
    public List<Level> getLevels() {
        return levels;
    }

    /**
     * This method update the current levels based from current grid (used in {@code undo})
     * @param level
     */
    public void updateLevels(Level level){
        levels.set(level.getIndex()-1,level);
    }

    /**
     * @setter set the movesCount
     * @param movesCount
     */
    public void setMovesCount(int movesCount) {
        this.movesCount = movesCount;
    }

    /**
     * @setter set the current level memo
     * @param deepClone
     */
    public void setCurrentLevelMemo(Level deepClone) {
        currentLevel = deepClone;
    }

    /**
     * @getter get isCurLevelComplete
     * @return isCurLevelComplete
     */
    public Boolean isCurLevelComplete(){return isCurLevelComplete;}

    /**
     * @getter get gameComplete
     * @return gameComplete
     */
    public boolean isGameComplete() {
        return gameComplete;
    }

    /**
     * The constructor of gameEngine.
     * It takes a file as input. If the file is valid, then load game file and initialize the levels.
     * @param input
     * @param production
     */
    public GameEngine(File input, boolean production) {
        try {
            logger = new GameLogger();
            levels = loadGameFile(input);
            currentLevel = getNextLevel();
        } catch (IOException x) {
            System.out.println("Cannot create logger.");
        } catch (NoSuchElementException e) {
            logger.warning("Cannot load the default save file: " + e.getStackTrace());
        }
    }

    /**
     * Default constructor for gameEngine
     */
    public GameEngine(){}


    /**
     * For developers to debug
     * @return
     */
    public static boolean isDebugActive() {
        return debug;
    }

    /**
     * The abstract method handle the key input
     * @param code
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public abstract void handleKey(KeyCode code)throws IOException, ClassNotFoundException;

    /**
     * The abstract method handle the move
     * @param delta
     * @param gameMode
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public abstract void move(Point delta,String gameMode) throws IOException, ClassNotFoundException;

    /**
     * This method set the {@code isCurLevelComplete} to false, and get the next level.
     */
    public void enterNext(){
        isCurLevelComplete = false;
        currentLevel = getNextLevel();
    }

    /**
     * This method load the game file and initialize the game grid, level name, map set name.
     * @param inputFile
     * @return
     * @throws FileNotFoundException
     */
    List<Level> loadGameFile(File inputFile) throws FileNotFoundException {
        List<Level> levels = new ArrayList<>(5);
        int levelIndex = 0;
        FileReader fr = new FileReader(inputFile);
        try (BufferedReader reader = new BufferedReader(fr)) {
            boolean parsedFirstLevel = false;
            List<String> rawLevel = new ArrayList<>();
            String levelName = "";
            while (true) { String line = reader.readLine();

                if (line == null) {
                    if (rawLevel.size() != 0) {
                        Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
                        levels.add(parsedLevel);
                    }
                    break;
                }

                if (line.contains("MapSetName")) {
                    mapSetName = line.replace("MapSetName: ", "");
                    continue;
                }

                if (line.contains("LevelName")) {
                    if (parsedFirstLevel) {
                        Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
                        levels.add(parsedLevel);
                        rawLevel.clear();
                    } else {
                        parsedFirstLevel = true;
                    }

                    levelName = line.replace("LevelName: ", "");
                    continue;
                }
                // <11.8> <add if-statement to enable "load saved file" compatible>
                if (line.contains("moveCount")){
                    String movesCountStr = line.replace("moveCount: ", "");
                    movesCount = Integer.parseInt(movesCountStr);
                    System.out.println("moveCount"+movesCount);
                }
                line = line.trim();
                line = line.toUpperCase();
                if (line.matches(".*W.*W.*")) {
                    rawLevel.add(line);
                }
            }
            fr.close();
        } catch (IOException e) {
            logger.severe("Error trying to load the game file: " + e);
        } catch (NullPointerException e) {
            logger.severe("Cannot open the requested file: " + e);
        }
        return levels;
    }



    /**
     * This method return null for the first time
     * level + 1 for other situation
     * @return next level
     */
    public Level getNextLevel() {
        if (currentLevel == null) {
            return levels.get(0);
        }
        int currentLevelIndex = currentLevel.getIndex();
        if (currentLevelIndex < levels.size()) {
            return levels.get(currentLevelIndex);
        }
        gameComplete = true;    // if gameComplete is true, then all levels are completed
        return null;
    }

    /**
     * This method turn on/off the toggle debug.
     */
    public void toggleDebug() {
        debug = !debug;
    }



}
