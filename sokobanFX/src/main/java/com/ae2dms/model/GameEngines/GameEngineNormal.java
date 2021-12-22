package com.ae2dms.model.GameEngines;

import com.ae2dms.model.GameGrid;
import com.ae2dms.model.GameLogger;
import com.ae2dms.model.GameObject;
import javafx.scene.input.KeyCode;
import java.awt.*;
import java.io.*;
import java.util.NoSuchElementException;

/**
 * This class is the normal game engine
 *
 * It contains two players and can manipulate two players' move.
 * It extends from {@code GameEngine}
 * @Author: Yuting He
 * @Version: 1.0
 */
public class GameEngineNormal extends GameEngine{

    /**
     * Constructor of GameEngineEntertain. It load the input gameFile and initialize the game
     * @param inputFile
     * @param production
     */
    public GameEngineNormal(File inputFile, boolean production) {
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
     * This method handle the keyboard input of the player.
     * It handles keeper moves.
     * @param code
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void handleKey(KeyCode code) throws IOException, ClassNotFoundException {
        switch (code) {
            case UP:
                move(new Point(-1, 0),"");
                break;

            case RIGHT:
                move(new Point(0, 1),"");
                break;

            case DOWN:
                move(new Point(1, 0),"");
                break;

            case LEFT:
                move(new Point(0, -1),"");
                break;

            default:
                // TODO: implement something funny.
        }

        if (isDebugActive()) {
            System.out.println(code);
        }
    }

    /**
     * This method judges the keeper moves
     * If the keeper moves, change the grids and add memo the level memo
     * @param delta
     * @param gameMode
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void move(Point delta, String gameMode)  {
        super.setKeeperToWall(false);
        levelMemo.addMemo(currentLevel,super.getMovesCount());
        if (isGameComplete()) {
            return;
        }

        Point keeperPosition = currentLevel.getKeeperPosition();
        GameObject keeper = currentLevel.objectsGrid.getGameObjectAt(keeperPosition);
        Point targetObjectPoint = GameGrid.translatePoint(keeperPosition, delta);
        GameObject keeperTarget = currentLevel.objectsGrid.getGameObjectAt(targetObjectPoint);

        if (GameEngineNormal.isDebugActive()) {
            System.out.println("Current level state:");
            System.out.println(currentLevel.toString());
            System.out.println("Keeper pos: " + keeperPosition);
            System.out.println("Movement source obj: " + keeper);
            System.out.printf("Target object: %s at [%s]", keeperTarget, targetObjectPoint);
        }

        boolean keeperMoved = false;


        switch (keeperTarget) {

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
                currentLevel.objectsGrid.putGameObjectAt(currentLevel.objectsGrid.getGameObjectAt(GameGrid.translatePoint(keeperPosition, delta)), keeperPosition);
                currentLevel.objectsGrid.putGameObjectAt(keeper, GameGrid.translatePoint(keeperPosition, delta));
                keeperMoved = true;
                break;

            case FLOOR:
                currentLevel.objectsGrid.putGameObjectAt(currentLevel.objectsGrid.getGameObjectAt(GameGrid.translatePoint(keeperPosition, delta)), keeperPosition);
                currentLevel.objectsGrid.putGameObjectAt(keeper, GameGrid.translatePoint(keeperPosition, delta));
                keeperMoved = true;
                break;

            default:
                logger.severe("The object to be moved was not a recognised GameObject.");
                throw new AssertionError("This should not have happened. Report this problem to the developer.");
        }

        if (keeperMoved) {
            keeperPosition.translate((int) delta.getX(), (int) delta.getY());
            super.setMovesCount(super.getMovesCount()+1);
            if (currentLevel.isComplete()) {
                if (isDebugActive()) {
                    System.out.println("Level complete!");
                }
                isCurLevelComplete = true;
            }
        }else{
            levelMemo.fetchPreMemo();// if keeper does not move, current status should be popped out of stack
        }
    }





}