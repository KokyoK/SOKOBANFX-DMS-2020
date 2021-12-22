package com.ae2dms.controller;

import com.ae2dms.Main;
import com.ae2dms.model.*;
import com.ae2dms.model.GameEngines.GameEngine;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.awt.*;
import java.util.Stack;
import java.awt.Robot;

import static javax.swing.SwingConstants.RIGHT;


/**
 * It provides two buttons and two hidden button <br/>
 * Button {@code btn}  will enter the next level <br/>
 * Button {@code displayBtn} will show the review gridPane and another two buttons {@code preStep} {@code postStep} <br/>
 * These two buttons allow user to review their game
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-13 16:34
 * This class control the window of level complete<br/>
 *

 **/
public class LevelCompleteController {

    /**
     * The complete message
     */
    @FXML
    private Label text;

    /**
     * The ok button
     */
    @FXML
    private Button btn;

    /**
     * The review game grid
     */
    @FXML
    private GridPane recordGrid;

    /**
     * The button which will start the game
     */
    @FXML
    private Button displayBtn;

    /**
     * The pre step button
     */
    @FXML
    private Button preStep;

    /**
     * The next step button
     */
    @FXML
    private Button postStep;

    @FXML
    private Label tipText;


    /**
     * The game engine of previous game
     */
    GameEngine gameEngine;

    /**
     * The level memo of previous level
     */
    Stack<Level> levelMemo = new Stack<>();

    /**
     * The reversed level memo (start from the first step)
     */
    Stack<Level> levelMemoReverse = new Stack<>();

    /**
     * The position calculator used to calculate the position of review grid
     */
    PositionCalculator positionCalculator = new PositionCalculator();

    /**
     * The current level
     */
    Level curLevel;

    /**
     * Event handler to handle the game gre
     */
    EventHandler<KeyEvent> eventHandler;

    /**
     * To see whether the event listener is turned on
     */
    Boolean isEventListenerOn = false;





    /**
     * Initialize the stage, pass gameEngine, levelMemos, and text<br/>
     * Set the keeper direction
     * @param Text
     * @param levelMemos
     * @param gameEngine
     * @throws InterruptedException
     */
    public void initializeAlert(String Text, LevelMemo levelMemos, GameEngine gameEngine) throws InterruptedException {
        text.setText(Text);
        this.levelMemo = levelMemos.getLevelStack();
        this.gameEngine = gameEngine;
        preStep.setVisible(false);
        postStep.setVisible(false);
        tipText.setVisible(false);

        GameImage gameImage = GameImage.getGameImage(Main.themeName);
        gameImage.setDirection(KeyCode.DOWN);
    }

    /**
     * Show the record Animation (by go to next step and previous step)<br/>
     * Firstly, reverse the level memo stack, and then set the buttons for go to next step and previous step disable
     * @author: Yuting He
     */
    public void showRecordAnimation() {
        initializeCalculator();
        displayBtn.setDisable(true);
        while (levelMemo.size() != 0) {
            levelMemoReverse.push(levelMemo.pop());
        }
        curLevel = levelMemoReverse.pop();
        reloadGrid(curLevel);
        preStep.setVisible(true);
        postStep.setVisible(true);
        tipText.setVisible(true);
        setEventFilter();

    }
    /**
     * Set event filter to listen whether the player do the next step or previous step<br/>
     * RIGHT for next step, LEFT for previous step
     * @author: Yuting He
     */
    private void setEventFilter() {
        Stage stage = (Stage)btn.getScene().getWindow();
        eventHandler = event -> {
            handleKey(event.getCode());
        };
        stage.addEventFilter(KeyEvent.KEY_PRESSED,eventHandler);
        isEventListenerOn = true;
    }

    /**
     * Handle the key code
     * RIGHT for next step, LEFT for previous step
     * @author: Yuting He
     * @param code
     */
    private void handleKey(KeyCode code) {
        if (code == KeyCode.LEFT && !preStep.isDisabled()){
            toPreStep();
        }else if(code == KeyCode.RIGHT && !postStep.isDisabled()){
            toPostStep();
        }
    }

    /**
     * Refresh the grid
     * This method shold be used after the current gameGrid has changed
     * @author: Yuting He
     * @param currentLevelStatus
     */
    private void reloadGrid(Level currentLevelStatus){
        Level.LevelIterator levelGridIterator = (Level.LevelIterator) currentLevelStatus.iterator();
        recordGrid.getChildren().clear();
        while (levelGridIterator.hasNext()) {
            addObjectToGrid(levelGridIterator.next(), levelGridIterator.getcurrentposition());
        }
        setGridPosition();
    }

    /**
     * This method is used to initialize the game grid by putting object onto the grid<br/>
     * Same as the add object grid in gameEngine
     * @author: Yuting He
     * @param gameObject
     * @param location
     */
    private void addObjectToGrid(GameObject gameObject, Point location) {
        double size = positionCalculator.calculateObjectSize();
        GraphicObject graphicObject = new GraphicObject(gameObject);
        graphicObject.setIv(size);
        recordGrid.add(graphicObject.iv, location.y, location.x);
    }

    private void setGridPosition(){
        double padding = positionCalculator.calculatePadding();
        if (padding > 0){
            recordGrid.setPadding(new javafx.geometry.Insets(0, (int)padding, 0, (int)padding));
        }else{
            recordGrid.setPadding(new javafx.geometry.Insets((int)-padding, 0,(int)-padding, 0));
        }
    }
    /**
     * This method initialize the calculator by passing the row number and column number to the grid <br/>
     * It fetch the {@code paneWidth} and {@code paneHeight}, also {@code COL} and {@code ROW},
     * Initialize the calculator with these parameters
     *
     */
    @FXML
    private void initializeCalculator() {
        double paneWidth = recordGrid.getBoundsInParent().getWidth();
        double paneHeight = recordGrid.getBoundsInParent().getHeight();
        int col = levelMemo.peek().objectsGrid.COLUMNS;
        int row = levelMemo.peek().objectsGrid.ROWS;

        positionCalculator.setPaneWidth(paneWidth);
        positionCalculator.setPaneHeight(paneHeight);
        positionCalculator.setCol(col);
        positionCalculator.setRow(row);
    }

    /**
     * This method is to handle when user click the button <br/>
     * It should just close the stage, and remove the event filter
     */
    @FXML
    private void buttonClicked(){
        Stage stage = (Stage)btn.getScene().getWindow();
        if (isEventListenerOn){
            stage.removeEventFilter(KeyEvent.KEY_PRESSED,eventHandler);
        }

        stage.close();
    }

    /**
     * This method used for the review part.
     *
     * It push a step into {@code levelMemoReverse} and pop a step from {@code levelMemo}
     * Also determine whether to set the button {@code preStep} disabled when the {@code levelMemoReverse} is empty
     */
    public void toPreStep() {
        levelMemoReverse.push(curLevel);
        curLevel = levelMemo.pop();
        reloadGrid(curLevel);
        if (levelMemo.size() == 0){
            preStep.setDisable(true);
        }
        postStep.setDisable(false);
    }

    /**
     * This method used for the review part.
     *
     * It push a step into {@code levelMemo} and pop a step from {@code levelMemoReverse}
     * Also determine whether to set the button {@code postStep} disabled when the {@code levelMemo is empty
     */
    public void toPostStep() {
        levelMemo.push(curLevel);
        curLevel = levelMemoReverse.pop();
        reloadGrid(curLevel);

        if (levelMemoReverse.size() == 0){
            postStep.setDisable(true);
        }
        preStep.setDisable(false);
    }
}
