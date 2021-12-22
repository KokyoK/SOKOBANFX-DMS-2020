package com.ae2dms.model;

import com.ae2dms.model.GameEngines.GameEngine;

import java.awt.*;
import java.io.Serializable;
import java.util.Iterator;

/**
 * This class is the game grid of the game.
 * It contains a 2D array of gameObjects.
 */
public class GameGrid implements Iterable,Serializable  {
    /**
     * The column count of the game grid.
     */
    public final int COLUMNS;

    /**
     * The row count of the game grid.
     */
    public final int ROWS;

    /**
     * The 2D array contains sets of game objects.
     */
    private GameObject[][] gameObjects;

    /**
     * Constructor of game grid.
     * @param columns
     * @param rows
     */
    public GameGrid(int columns, int rows) {
        COLUMNS = columns;
        ROWS = rows;
        gameObjects = new GameObject[COLUMNS][ROWS];
    }

    /**
     * This method translate source location to thw point
     * @param sourceLocation
     * @param delta
     * @return translated point
     */
    public static Point translatePoint(Point sourceLocation, Point delta) {
        Point translatedPoint = new Point(sourceLocation);
        translatedPoint.translate((int) delta.getX(), (int) delta.getY());
        return translatedPoint;
    }

    /**
     * This method get the dimensions of the game Grid.
     * @return
     */
    public Dimension getDimension() {
        return new Dimension(COLUMNS, ROWS);
    }

    /**
     * This method get the targeted game Object from a specific source
     * @param source
     * @param delta
     * @return gameObject
     */
    GameObject getTargetFromSource(Point source, Point delta) {
        return getGameObjectAt(translatePoint(source, delta));
    }

    /**
     *  This method get the targeted game Object from a specific location
     * @param col
     * @param row
     * @return Game object
     * @throws ArrayIndexOutOfBoundsException
     */
    public GameObject getGameObjectAt(int col, int row) throws ArrayIndexOutOfBoundsException {
        if (isPointOutOfBounds(col, row)) {
            if (GameEngine.isDebugActive()) {
                System.out.printf("Trying to get null GameObject from COL: %d  ROW: %d", col, row);
            }
            throw new ArrayIndexOutOfBoundsException("The point [" + col + ":" + row + "] is outside the map.");
        }
        return gameObjects[col][row];
    }

    /**
     * This method get the game object at a point
     * @param  p
     * @return
     */
    public GameObject getGameObjectAt(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null.");
        }
        return getGameObjectAt((int) p.getX(), (int) p.getY());
    }

    /**
     * This method remove the object ar point position
     * @param position
     */
    public boolean removeGameObjectAt(Point position) {
        return putGameObjectAt(null, position);
    }

    /**
     * This method put the game object at (x,y)
     * @param gameObject
     * @param x
     * @param y
     * @return true if success, false otherwise
     */
    public boolean putGameObjectAt(GameObject gameObject, int x, int y) {
        if (isPointOutOfBounds(x, y)) {
            return false;
        }

        gameObjects[x][y] = gameObject;
        return gameObjects[x][y] == gameObject;
    }

    /**
     * This method puts a game object at a point p
     * @param gameObject
     * @param p
     * @return true if success, false otherwise
     */
    public boolean putGameObjectAt(GameObject gameObject, Point p) {
        return p != null && putGameObjectAt(gameObject, (int) p.getX(), (int) p.getY());
    }

    /**
     * This method check if the point is out of bounds using the point location
     * @param x
     * @param y
     * @return
     */
    private boolean isPointOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= COLUMNS || y >= ROWS);
    }

    /**
     * This method check if the point is out of bounds using the point
     * @param p
     * @return
     */
    private boolean isPointOutOfBounds(Point p) {
        return isPointOutOfBounds(p.x, p.y);
    }

    /**
     * This method change the grid to string format.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(gameObjects.length);

        for (GameObject[] gameObject : gameObjects) {
            for (GameObject aGameObject : gameObject) {
                if (aGameObject == null) {
                    aGameObject = GameObject.DEBUG_OBJECT;
                }
                sb.append(aGameObject.getCharSymbol());
            }

            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * This method return the game iterator
     * @return
     */
    @Override
    public Iterator<GameObject> iterator() {
        return new GridIterator();
    }

    public class GridIterator implements Iterator<GameObject> {
        int row = 0;
        int column = 0;

        /**
         * This method check if the iterator has reached the end
         * @return true if it reaches the end, false otherwise
         */
        @Override
        public boolean hasNext() {
            return !(row == ROWS && column == COLUMNS);
        }

        /**
         * This method return the next point in the 2D map
         * @return
         */
        @Override
        public GameObject next() {
            if (column >= COLUMNS) {
                column = 0;
                row++;
            }
            return getGameObjectAt(column++, row);
        }
    }

}