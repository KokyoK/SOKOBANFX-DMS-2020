package com.ae2dms.model;

import com.ae2dms.model.GameEngines.GameEngine;

import java.awt.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * This class is the Level of the game.
 * It has two gameGrid, index and levelName.
 *
 **/
public final class Level implements Iterable<GameObject>, Serializable {
    /**
     * The grid for object except diamond
     */
    public  GameGrid objectsGrid;
    /**
     * The grid for diamond
     */
    public  GameGrid diamondsGrid;
    /**
     * The level's name
     */
    private final String name;
    /**
     * The level's index in current map set
     */
    private final int index;
    /**
     * Number of diamonds in diamdGrid
     */
    private int numberOfDiamonds = 0;
    /**
     * Keeper's position in object grid
     */
    private Point keeperPosition = new Point(0, 0);
    /**
     * Friend's position in object grid (if friend exists)
     */
    private Point friendPosition = new Point(0,0);

    /**
     * The default constructor of level.
     * It accepts the level name, level index and raw_level, initialize the two grids and level index.
     * @param levelName
     * @param levelIndex
     * @param raw_level
     */
    public Level(String levelName, int levelIndex, List<String> raw_level) {
        if (GameEngine.isDebugActive()) {
            System.out.printf("[ADDING LEVEL] LEVEL [%d]: %s\n", levelIndex, levelName);

        }
        name = levelName;
        index = levelIndex;

        int rows = raw_level.size();
        int columns = raw_level.get(0).trim().length();

        objectsGrid = new GameGrid(rows, columns);
        diamondsGrid = new GameGrid(rows, columns);
        /**
         * objectGrid: save object
         * diamondGrid: save diamond
         */
        for (int row = 0; row < raw_level.size(); row++) {

            for (int col = 0; col < raw_level.get(row).length(); col++) {
                GameObject curTile = GameObject.fromChar(raw_level.get(row).charAt(col));   // traverse the map

                if (curTile == GameObject.DIAMOND) {
                    numberOfDiamonds++;
                    diamondsGrid.putGameObjectAt(curTile, row, col);
                    curTile = GameObject.FLOOR;
                } else if (curTile == GameObject.KEEPER) {
                    keeperPosition = new Point(row, col);
                } else if (curTile == GameObject.FRIEND){
                    friendPosition = new Point(row, col);
                }else if (curTile == GameObject.CRATE_ON_DIAMOND) {
                    numberOfDiamonds++;
                    diamondsGrid.putGameObjectAt(GameObject.DIAMOND, row, col);
                    curTile = GameObject.CRATE;
                }
                objectsGrid.putGameObjectAt(curTile, row, col);
                if (curTile == GameObject.KEEPER_ON_DIAMOND){
                    objectsGrid.putGameObjectAt(GameObject.KEEPER, row, col);
                    diamondsGrid.putGameObjectAt(GameObject.DIAMOND, row, col);
                    keeperPosition = new Point(row, col);
                    numberOfDiamonds++;
                }
                if (curTile == GameObject.FRIEND_ON_DIAMOND){
                    objectsGrid.putGameObjectAt(GameObject.FRIEND, row, col);
                    diamondsGrid.putGameObjectAt(GameObject.DIAMOND, row, col);
                    friendPosition = new Point(row, col);
                    numberOfDiamonds++;
                }
                curTile = null;
            }
        }
    }

    /**
     * This method checks if the game is completed.
     * It conpares the {@code numberOfDiaminds} and the {@code crateOnDiamonds}
     * @return isGameComplete
     */
    public boolean isComplete() {
        int cratedDiamondsCount = 0;
        for (int row = 0; row < objectsGrid.ROWS; row++) {
            for (int col = 0; col < objectsGrid.COLUMNS; col++) {
                if (objectsGrid.getGameObjectAt(col, row) == GameObject.CRATE && diamondsGrid.getGameObjectAt(col, row) == GameObject.DIAMOND) {
                    cratedDiamondsCount++;
                }
            }
        }
        return cratedDiamondsCount >= numberOfDiamonds;
    }

    /**
     * @getter get the level name
     * @return levelName
     */
    public String getName() {
        return name;
    }

    /**
     * @getter get the index
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @getter get keeper position
     * @return keeperPosition
     */
    public Point getKeeperPosition() {
        return keeperPosition;
    }

    /**
     * @getter get friend position
     * @return friendPosition
     */
    public Point getFriendPosition()  {return friendPosition; }

    /**
     * get the gameObject at the target position
     * @param source
     * @param delta
     * @return
     */
    public GameObject getTargetObject(Point source, Point delta) {
        return objectsGrid.getTargetFromSource(source, delta);
    }

    /**
     * This method only toString the object grid.
     * @return
     */
    @Override
    public String toString() {
        return objectsGrid.toString();
    }

    /**
     * get the iterator for levels
     * @return
     */
    @Override
    public Iterator<GameObject> iterator() {
        return new LevelIterator();
    }

    /**
     * The inner class levelIterator, traverse all levels and all objects in it
     */
    public class LevelIterator implements Iterator<GameObject> {

        int column = 0;
        int row = 0;

        /**
         * Check if current object is the last object in the grid
         * @return
         */
        @Override
        public boolean hasNext() {
            return !(row == objectsGrid.ROWS - 1 && column == objectsGrid.COLUMNS);
        }

        @Override
        public GameObject next() { if (column >= objectsGrid.COLUMNS) {
            column = 0;
            row++;
        }
        GameObject object = objectsGrid.getGameObjectAt(column, row);
        GameObject diamond = diamondsGrid.getGameObjectAt(column, row);
        GameObject retObj = object;
        column++;
            if (diamond == GameObject.DIAMOND) {
                if (object == GameObject.CRATE) {
                    retObj = GameObject.CRATE_ON_DIAMOND;
                }else if (object == GameObject.KEEPER) {
                    retObj = GameObject.KEEPER_ON_DIAMOND;
                }else if(object == GameObject.FRIEND){
                    retObj = GameObject.FRIEND_ON_DIAMOND;
                }
                else if (object == GameObject.FLOOR) {
                    retObj = diamond;
                } else {
                    retObj = object;
                }
            }
            return retObj;
        }

        /**
         * @getter get current position
         * @return currentPosition in point format
         */
        public Point getcurrentposition() {
            return new Point(column-1, row);
        }
    }
}