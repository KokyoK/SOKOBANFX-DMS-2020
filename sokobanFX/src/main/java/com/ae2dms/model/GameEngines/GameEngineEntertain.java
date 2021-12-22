package com.ae2dms.model.GameEngines;

import com.ae2dms.model.GameGrid;
import com.ae2dms.model.GameLogger;
import com.ae2dms.model.GameObject;
import javafx.scene.input.KeyCode;
import java.awt.*;
import java.io.*;
import java.util.NoSuchElementException;

/**
 * This class is the Entertain game engine
 *
 * It contains two players and can manipulate two players' move.
 * It extends from {@code GameEngine}
 * @Author: Yuting He
 * @Version: 1.0
 */
public class GameEngineEntertain extends GameEngine {

    /**
     * Constructor of GameEngineEntertain. It load the input gameFile and initialize the game
     * @param inputFile
     * @param production
     */
    public GameEngineEntertain(File inputFile, boolean production) {
        try {
            logger = new GameLogger();
            levels = loadGameFile(inputFile);
            currentLevel = getNextLevel();
        } catch (IOException x) {
            System.out.println("Cannot create logger.");
        } catch (NoSuchElementException e) {
            logger.warning("Cannot load the default save file: " + e.getStackTrace());
        }
    }

    /**
     * This method handle the keyboard input of the players.
     * It handles both keeper move and friend moves.
     * @param code
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void handleKey(KeyCode code) throws IOException, ClassNotFoundException {
        switch (code) {
            case UP:
                move(new Point(-1, 0),"keeper");
                break;
            case W:
                move(new Point(-1, 0),"friend");
                break;

            case RIGHT:
                move(new Point(0, 1),"keeper");
                break;
            case D:
                move(new Point(0, 1),"friend");
                break;

            case DOWN:
                move(new Point(1, 0),"keeper");
                break;
            case S:
                move(new Point(1, 0),"friend");
                break;

            case LEFT:
                move(new Point(0, -1),"keeper");
                break;
            case A:
                move(new Point(0, -1),"friend");
                break;


            default:
                // TODO: implement something funny.
        }

        if (isDebugActive()) {
            System.out.println(code);
        }
    }

    /**
     * This method judges whether the keeper and friend moves.
     * If they move, change the grids, and add memo to the levelMemo.
     * @param delta
     * @param type
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void move(Point delta, String type)  {
        super.setKeeperToWall(false);
        if (isGameComplete()) {
            return;
        }
        Point playerPosition;
        if(type == "keeper"){
            playerPosition = currentLevel.getKeeperPosition();
        }else{
            playerPosition = currentLevel.getFriendPosition();
        }
        GameObject keeper = currentLevel.objectsGrid.getGameObjectAt(playerPosition);
        Point targetObjectPoint = GameGrid.translatePoint(playerPosition, delta);
        GameObject keeperTarget = currentLevel.objectsGrid.getGameObjectAt(targetObjectPoint);

        if (GameEngine.isDebugActive()) {
            System.out.println("Current level state:");
            System.out.println(currentLevel.toString());
            System.out.println("Keeper pos: " + playerPosition);
            System.out.println("Movement source obj: " + keeper);
            System.out.printf("Target object: %s at [%s]", keeperTarget, targetObjectPoint);
        }

        boolean keeperMoved = false;
        levelMemo.addMemo(currentLevel,super.getMovesCount());

        switch (keeperTarget) {

            case FRIEND:
            case KEEPER:
                break;
            case WALL:
                super.setKeeperToWall(true);
                break;

            case CRATE:

                GameObject crateTarget = currentLevel.getTargetObject(targetObjectPoint, delta);
                if (crateTarget != GameObject.FLOOR) {
                    break;
                }

                currentLevel.objectsGrid.putGameObjectAt(currentLevel.objectsGrid.getGameObjectAt(GameGrid.translatePoint(targetObjectPoint, delta)), targetObjectPoint);
                currentLevel.objectsGrid.putGameObjectAt(keeperTarget, GameGrid.translatePoint(targetObjectPoint, delta));
                currentLevel.objectsGrid.putGameObjectAt(currentLevel.objectsGrid.getGameObjectAt(GameGrid.translatePoint(playerPosition, delta)), playerPosition);
                currentLevel.objectsGrid.putGameObjectAt(keeper, GameGrid.translatePoint(playerPosition, delta));
                keeperMoved = true;
                break;

            case FLOOR:
                currentLevel.objectsGrid.putGameObjectAt(currentLevel.objectsGrid.getGameObjectAt(GameGrid.translatePoint(playerPosition, delta)), playerPosition);
                currentLevel.objectsGrid.putGameObjectAt(keeper, GameGrid.translatePoint(playerPosition, delta));
                keeperMoved = true;
                break;

            default:
                logger.severe("The object to be moved was not a recognised GameObject.");
                throw new AssertionError("This should not have happened. Report this problem to the developer.");
        }

        if (keeperMoved) {
            System.out.println("1111111");
            playerPosition.translate((int) delta.getX(), (int) delta.getY());
            super.setMovesCount(super.getMovesCount()+1);
            if (currentLevel.isComplete()) {
                isCurLevelComplete = true;
            }
        }else{
            levelMemo.fetchPreMemo();// if keeper does not move, current status should be popped out of stack
        }
    }



}