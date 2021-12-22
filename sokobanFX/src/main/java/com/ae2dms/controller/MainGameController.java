package com.ae2dms.controller;
import com.ae2dms.Main;
import com.ae2dms.controller.GameInitializer.GameInitializer;
import com.ae2dms.controller.GameInitializer.GameInitializerFactory;
import com.ae2dms.controller.GameInitializer.ResetGameInitializer;
import com.ae2dms.model.*;
import com.ae2dms.model.GameEngines.GameEngine;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.util.List;

import static com.ae2dms.model.GameEngines.GameEngine.isDebugActive;

/**
 * Firstly, the method {@code showModeChoice will be called. Player should choose the mode before entering the game}<br>
 * Then the game should start, provide the menu bar and a button {@code backToMenu } for player to click
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-07 23:03
 * @version: 2.2.0
 * This class is the controller of the main game<br/>
 *

 **/
public class MainGameController {
    /**
     * The primary stage of the game
     */
    public Stage primaryStage;

    /**
     * The game engine of game. Each {@code mainGameController} should contain only one game engine
     */
    public  GameEngine gameEngine;

    /**
     * The mapSet name. If changed or preset by other classes, it should be changes
     * Otherwise, the default map set name should be Simple.skb
     */
    public  String mapSet = "Simple.skb";

    /**
     * Listen and handle the keycode pressed event.
     */
    private EventHandler<KeyEvent> eventHandler;

    /**
     * {@code gameType} contains five type: newGame, continueGame, customize, resetGame,Entertain game
     */
    private String gameType;

    /**
     * The boolean value to judge if the game is of HellMode. <br/>
     * (Hell mode is wall of Air mode, where player cannot see the wall unless they crash onto the wall)
     *
     */
    private Boolean isHellMode = false;

    /**
     * {@code gameInitializeFactory } is to create set of game initializer of the game
     */
    GameInitializerFactory gameInitializerFactory = new GameInitializerFactory();

    /**
     * The game image for each object, and should be initialized using different game theme
     */
    GameImage gameImage = new GameImage(Main.themeName);

    /**
     * The position calculator, used for calculating the size of game object and the position of the pane
     */
    PositionCalculator positionCalculator = new PositionCalculator();

    /**
     * The grid pane of the game
     */
    @FXML
    public GridPane gameGrid;

    /**
     * The bound of grid pane, used to calculate size and position of the game grid
     */
    @FXML
    public Pane pane;

    /**
     * The moves count label in the right info Pane.<br/>
     * Should be changed when kepper moved
     */
    @FXML
    private Label moveNum;

    /**
     * The radio button of toggle music<br/>
     * Should be connected to the toggle music button in the start page
     */
    @FXML
    public RadioMenuItem musicRadio;

    /**
     * The Undo button in menu
     */
    @FXML
    private javafx.scene.control.MenuItem undoMenu;

    /**
     * The label show the current map set Name in the right pane
     */
    @FXML
    private Label mapSetText;

    /**
     * The label show the current level Name in the right pane
     */
    @FXML
    private Label levelText;

    /**
     * The memo of each step that user do. Will not contains the undoed step.
     */
    LevelMemo levelMemo = LevelMemo.getLevelMemo();

    /**
     * This method initialize the controller when the page first shown
     */
    @FXML
    public void initialize() {
        menuBarInitialize();
        //gameImage.setThemeName(Main.themeName);
    }

    /**
     *
     * This method initialize the menuBar in the main game page.<br/>
     *
     * Set {@code musicRadio} based on the previous pages music radio
     * When the levelMemo is empty, disable the Menu {@code undoMenu}.
     */
    @FXML
    private void menuBarInitialize() {
        GameMusic gameMusic = GameMusic.getGameMusic();
        if (gameMusic.getClipStatus()) {
            musicRadio.setSelected(true);
        }
        if (levelMemo.isEmpty()) {
            undoMenu.setDisable(true);
        }
    }

    /**
     * This method initialize the calculator by passing the row number and column number to the grid.
     *
     * version:1.0.0<br>
     * It fetch the {@code paneWidth} and {@code paneHeight}, also {@code COL} and {@code ROW},
     * Initialize the calculator with above four parameters
     */
    @FXML
    private void initializeCalculator() {
        double paneWidth = gameGrid.getBoundsInParent().getWidth();
        double paneHeight = gameGrid.getBoundsInParent().getHeight();
        int col = gameEngine.getCurrentLevel().objectsGrid.COLUMNS;
        int row = gameEngine.getCurrentLevel().objectsGrid.ROWS;

        positionCalculator.setPaneWidth(paneWidth);
        positionCalculator.setPaneHeight(paneHeight);
        positionCalculator.setCol(col);
        positionCalculator.setRow(row);

    }

    /**
     * This method add object to grid one by one.
     *
     * This method firstly call the {@code} initializeCalculator and calculate the object size
     * before adding them to the grid iteratively.
     * version:1.0.0<br/>
     * It accepts the game object and location, add the {@code gameObject} to the {@code location}<br>
     * <br><br>
     * version 1.1.0<br/>
     * Change the add graphic object using shape to image.<br/>
     * <br><br>
     * version 1.1.1<br/>
     * Add calculate object size in this case.<br>
     * Calculate the object size and grid pane location using calculator
     *
     * @param gameObject
     * @param location
     */
    private void addObjectToGrid(GameObject gameObject, Point location) {
        initializeCalculator();
        double size = positionCalculator.calculateObjectSize();
        GraphicObject graphicObject = new GraphicObject(gameObject);
        graphicObject.setIv(size);
        gameGrid.add(graphicObject.iv, location.y, location.x);
    }


    /**
     * This method is the entrance of the game.
     * <br><br>
     * version:1.0.0<br>
     * Start game directly by calling the {@code initializeGame}, {@code setFxmlLabel}, {@code setEventFilter}<br>
     * <br><br>
     *
     * version:1.1.0<br>
     * Add {@code showModeChoice} before {@code initializeGame}<br/>
     * <br><br>
     *
     * version:1.1.1<br>
     * Pass the {@code gameType} to the game before {@code showModeChoice}<br>
     *
     * @param primaryStage
     * @param gameType
     * @throws IOException
     */
    public void gameStart(Stage primaryStage, String gameType) throws IOException {
        this.primaryStage = primaryStage;
        this.gameType = gameType;

        showModeChoice();
        initializeGame();
        setFxmlLabel();
        setEventFilter();
    }

    /**
     * This method show mode choice before start the game.
     *
     * version:1.0.0<br/>
     * Show a separated stage that provide mode choice for
     * @throws IOException
     */
    private void showModeChoice() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModeChoiceAlert-layout.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        ModeChoiceAlertController modeChoiceAlertController = loader.getController();
        isHellMode = modeChoiceAlertController.getHellMode();
        if (isHellMode) {
            gameImage.hideWallImg();
        }else{
            gameImage.showWallImg();
        }
    }

    /**
     * This method set FXML label.
     *
     * version:1.0.0<br>
     *      Set two labels: map set Text, level Text.
     * <br><br>
     * version:1.0.1<br>
     * Set moves count of the current level.
     */
    @FXML
    private void setFxmlLabel() {
        mapSetText.setText(mapSet.substring(0, mapSet.length() - 4));
        levelText.setText(gameEngine.getCurrentLevel().getName());
        moveNum.setText(String.valueOf(gameEngine.getMovesCount()));
    }


    /**
     * Initialize game by generate a {@code gameInitializer } using {@code gameInitializerFactory}.
     * version:1.0.0<br>
     * Initialize the game and get mapset from initializer
     * <br><br>
     * version:1.0.1<br>
     * Fix Bug: when the gameEngine is null (in the customize case, user canceled choosing the mapset)
     * <br>
     *     It should not initialize the game and return.
     * @throws IOException
     */
    void initializeGame() throws IOException {
        GameInitializer gameInitializer = gameInitializerFactory.getGameInitializer(gameType);
        mapSet = gameInitializer.getMapSet();
        System.out.println(mapSet);
        gameEngine = gameInitializer.getGameEngine();
        if(gameEngine == null){
            backToMenu();
            return;
        }
        gameEngine.setHellMode(isHellMode);
        reloadGrid();

        undoMenu.setDisable(true);
        levelMemo.clearMemo();
    }

    /**
     * Initialize the reset game.
     * version:1.0.0<br>
     * The game engine should be redirected to a new gameEngine. <br>
     *     clear the previous levelMemo, set undo disabled, and setThe mode based on precious mode.
     * @param mapSet
     */
    void initializeResetGame(String mapSet){
        ResetGameInitializer resetGameInitializer = new ResetGameInitializer(mapSet);
        gameEngine = resetGameInitializer.getGameEngine();
        gameEngine.setHellMode(isHellMode);
        reloadGrid();

        undoMenu.setDisable(true);
        levelMemo.clearMemo();
    }

    /**
     * This method define an event filter and add it to the stage.
     *
     * It call the
     * version:1.0.0<br>
     *     Call the {@code handleKey} when getting the keycode.<br>
     * <br><br>
     * version:1.1.0<br>
     * If current level has completed, clear the {@code levelMemo},also call{@showNextLevel} method.
     * <br><br>
     * version:1.1.1<br>
     * Separate the definition of the eventFilter and adding of it.
     * version:1.2.0<br>
     * Add content related to the Hell mode. If the keeper crash into the wall, should call the {@code showWallAnimation}
     */
    @FXML
    public void setEventFilter() {

        eventHandler = event -> {
            if(gameEngine.isGameComplete()) {
                return;
            }
            try {
                setKeeperDirection(event.getCode());
                gameEngine.handleKey(event.getCode());
                if (gameEngine.isCurLevelComplete()){
                    reloadGrid();
                    undoMenu.setDisable(true);
                    levelMemo.addMemo(gameEngine.currentLevel, gameEngine.getMovesCount());
                    showNextLevelMsg();
                    levelMemo.clearMemo();
                }
                if (gameEngine.getHellMode()){
                    gameImage.hideWallImg();
                    if (gameEngine.getKeeperToWall())
                        showWallAnimation();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moveNum.setText(String.valueOf(gameEngine.getMovesCount()));
            if (!levelMemo.isEmpty()){
                undoMenu.setDisable(false);
            }
            reloadGrid();
        };
         primaryStage.addEventFilter(KeyEvent.KEY_PRESSED,eventHandler);

    }


    /**
     * This method show the wall animation (blink) when keeper crash into wall in the hell mode.
     *
     * This method should never be used in normal mode<br>
     * After playing the animation, the wall image will remain on the map until the keeper move the next step.<br>
     *
     */
    private void showWallAnimation()  {
        System.out.println("animationHere");
        gameImage.showWallImg();
        reloadGrid();
        FadeTransition ft = new FadeTransition(Duration.millis(300),gameGrid);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();
    }

    /**
     * This method show a separated stage, containing next level message.
     *
     * It should wait for the user to close the new stage, then go to the next level.<br/>
     * The {@code gameEngine.enterNext} should also be called in this method after waiting.
     * @throws IOException
     * @throws InterruptedException
     */
    private void showNextLevelMsg() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/LevelComplete-layout.fxml"));
        Parent root = loader.load();

        Stage alertStage = new Stage();
        alertStage.setScene(new Scene(root));
        alertStage.initModality(Modality.APPLICATION_MODAL);
        LevelCompleteController levelCompleteController = loader.getController();
        String alertText = gameEngine.getCurrentLevel().getName()+" ! \n";
        levelCompleteController.initializeAlert(alertText,levelMemo,gameEngine);
        alertStage.showAndWait();
        gameEngine.enterNext();
    }

    /**
     * This method is used to set the keeper's direction.
     *
     * It is called when {@code setEventFilter}<br>
     *     The keeper's / friend's direction should be always set no matter whether they moved<br>
     *     Here using Singleton design pattern, and get the instance of{@code gameImage}
     * @param code
     */
    private void setKeeperDirection(KeyCode code) {
        GameImage gameImage = GameImage.getGameImage(Main.themeName);
        gameImage.setDirection(code);
    }


    /**
     * This method is bind with an FXML menuItem, and load customize game.
     *
     * It set the {@code gameType } to "customize" and initialize game again.
     *
     * @throws IOException
     */
    public void loadGame() throws IOException {
        gameType = "customize";
        initializeGame();
    }

    /**
     * This method is to show the victory message of the game.
     *
     * It will only show when user finish the current map set.
     * This method will show a new stage and wait for that stage to close before continue (back to the main menu)
     * @throws IOException
     */
    @FXML
    private void showVictoryMessage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Victory-layout.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        VictoryController victoryController = loader.getController();
        victoryController.setVicMsg(gameEngine.getMovesCount(), mapSet);
        victoryController.savePrimaryStage(primaryStage);
        stage.showAndWait();
        primaryStage.removeEventFilter(KeyEvent.KEY_PRESSED,eventHandler);
    }

    /**
     * This method is used to set the {@code gameGrid} position to the center of the pane.
     *
     * It first call the {@code positionCalculator} to calculate the padding,
     * and judge if the padding if for left and right, or top and bottom.
     * After calling the method, the {@code gameGrid} should be at the center of the bound pane
     */
    private void setGridPosition(){
        initializeCalculator();
        double padding = positionCalculator.calculatePadding();
        if (padding > 0){
            gameGrid.setPadding(new javafx.geometry.Insets(0, (int)padding, 0, (int)padding));
        }else{
            gameGrid.setPadding(new javafx.geometry.Insets((int)-padding, 0,(int)-padding, 0));
        }
    }

    /**
     * This method is to reload the grid, set the model and view synchronous.
     *
     * version 1.0.0:<br>
     *  reload the grid and set them to the view by adding {@code graphicObject} iteratively.<br>
     *      <br><br>
     *  version:1.0.1<br>
     * If the game is completed, if will show victory msg and not reload the Grid.
     */
    public void reloadGrid() {
        if (gameEngine.isGameComplete()) {  // if all stages are completed
            try {showVictoryMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        setGridPosition();
        Level currentLevel = gameEngine.getCurrentLevel();
        Level.LevelIterator levelGridIterator = (Level.LevelIterator) currentLevel.iterator();
        gameGrid.getChildren().clear();
        while (levelGridIterator.hasNext()) {
            addObjectToGrid(levelGridIterator.next(), levelGridIterator.getcurrentposition());
        }
        gameGrid.autosize();
        primaryStage.sizeToScene();
        setFxmlLabel();
    }

    /**
     * This method bind with FXML element MenuItem. It will exit the program.
     *
     * The status code of exiting is 0.
     */
    public void closeGame() {
        System.exit(0);
    }

    /**
     * This method saves the current file (start from current level) and moves count.
     * It will save from the current level to the end of the level list.
     * The target file is "SavedGame.skb", and this file will be used to store game info each time.
     * This method uses {@code FileWriter} instead of {@code output stream} is because it should deal with the buffer storage problem.
     * @throws IOException
     */
    public void saveGame() throws IOException {
        File SavedFile = new File("src/main/resources/level/SavedGame.skb");

        if (!SavedFile.exists()) {
            SavedFile.createNewFile();
        }
        Level curLevel = gameEngine.getCurrentLevel();

        if (SavedFile.exists()) {
            System.out.println("file exists");
            FileWriter fw = new FileWriter(SavedFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            if (isDebugActive()) {
                System.out.println(curLevel);
            }
            List<Level> levels = gameEngine.getLevels();       // get level list
            // traverse the levels list
            for (int i = curLevel.getIndex() - 1; i < levels.size(); i++) {
                Level processingLevel = levels.get(i);
                GameGrid curGrid = combineGrid(processingLevel);
                bw.write("LevelName: " + processingLevel.getName() + "\n");
                bw.write(curGrid.toString());
            }

            // write into file
            bw.write("moveCount: ");
            bw.write(gameEngine.getMovesCount() + "");
            bw.close();
            fw.close();
            System.out.println("saved successfully!");
        } else {
            if (isDebugActive()) {
                System.out.println("the file does not exists");
            }
        }
    }

    /**
     * This method combine objectGrid and diamondGrid of curLevel.
     *
     * This is specifically used when save the game. the saved game should be able to be loaded same way as the normal game.
     *
     * @param curLevel
     * @return combined gameGrid
     */
    public GameGrid combineGrid(Level curLevel) {
        GameGrid combinedGrid = new GameGrid(curLevel.objectsGrid.COLUMNS, curLevel.objectsGrid.ROWS);
        Level.LevelIterator levelGridIterator = (Level.LevelIterator) curLevel.iterator();

        while (levelGridIterator.hasNext()) {
            combinedGrid.putGameObjectAt(levelGridIterator.next(), levelGridIterator.getcurrentposition());
        }
        return combinedGrid;
    }


    /**
     * This method reset the current level and all the related status should gi back to the beginning
     *
     * This method will reset the{@code moveCount} to the start of this Level.
     * Reset the {@code mapset} to the start of the game. It is done by re-initialize the game (load from file).
     * And set {@code currentLevel} to current level
     */
    public void resetLevel() {
        // store current level info
        Level currentLevel = gameEngine.getCurrentLevel();
        int levelIdx = currentLevel.getIndex();
        int moveCount = levelMemo.fetchInitialMove();
        this.gameEngine = null;
        initializeResetGame(mapSet);

        // set previous level info
        gameEngine.setCurrentLevel(levelIdx);
        gameEngine.setMovesCount(moveCount);
        levelMemo.clearMemo();
        System.out.println(gameEngine.getCurrentLevel().getIndex() + " " + gameEngine.getMovesCount());
        reloadGrid();
        undoMenu.setDisable(true);
    }

    /**
     * This method show the about page of the game.
     * It show a new stage, and the current stage and the new stage have no relationship,
     * which means the current stage can still be operated if the new stage is not closed
     * @throws IOException
     */
    public void showAbout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AboutGame-layout.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * This method turn on/off the music.
     * As the gameMusic is of Singleton design pattern, it should switch the status of the music.
     * If the music is turned on, then it should be turned off after this method executed, and vice-versa.
     */
    public void toggleMusic() {
        GameMusic gameMusic = GameMusic.getGameMusic();
        gameMusic.switchMusicStatus();
    }

    /**
     * This method is used for developers to debug.
     * It will print some messages, which might be helper for developer's to debug
     */
    public void toggleDebug() {
        gameEngine.toggleDebug();
        reloadGrid();
    }

    /**
     * This method is connected to the menuItem undo in the menuBar.
     * It set the move count -1, and updated the levels game grid, which is poped from the level memo.
     * When the levelMemo has no memory, which means it is the first step of the memory, it will set the menuItem disabled.
     */
    public void undoStep() {
        System.out.println("undo");
        Level prevStatus = levelMemo.fetchPreMemo();
        gameEngine.setCurrentLevelMemo((Level) levelMemo.deepClone(prevStatus));
        gameEngine.updateLevels(gameEngine.getCurrentLevel());
        gameEngine.setMovesCount(gameEngine.getMovesCount()-1);
        reloadGrid();
        if (levelMemo.isEmpty()) {
            undoMenu.setDisable(true);
        }
    }

    /**
     * This method is bind with a button, allow user to quit current game,and  go back to the main menu.
     * It also remove the event filter in this page.
     * @throws IOException
     */
    public void backToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/StartPage-layout.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.removeEventFilter(KeyEvent.KEY_PRESSED,eventHandler);

    }
}
