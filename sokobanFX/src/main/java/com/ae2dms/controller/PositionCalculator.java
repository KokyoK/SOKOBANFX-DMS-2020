package com.ae2dms.controller;

/**
 * This class is a calculator to do calculation of position of pane and the size of game object.
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-24 15:52
 **/
public class PositionCalculator {
    /**
     * Bounded pane width
     */
    private double paneWidth;

    /**
     * Bounded pane height
     */
    private double paneHeight;

    /**
     * Column number of {@code objectGrid}
     */
    private int col;

    /**
     * Row number of {@code objectGrid}
     */
    private int row;

    /**
     * @setter set the pane height {@code paneHeight}
     * @param paneHeight
     */
    public void setPaneHeight(double paneHeight) {
        this.paneHeight = paneHeight;
    }

    /**
     * @setter set the pane width {@code paneWidth}
     * @param paneWidth
     */
    public void setPaneWidth(double paneWidth) {
        this.paneWidth = paneWidth;
    }

    /**
     * @getter set the pane height
     * @return {@code paneHeight}
     */
    public double getPaneHeight() {
        return paneHeight;
    }

    /**
     * @getter get the pane width
     * @return {@code paneWidth}
     */
    public double getPaneWidth() {
        return paneWidth;
    }

    /**
     * @setter set the column number {@code COL}
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * @setter set the row number {@code ROW}
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Calculate the object size based on pane width and height, and object number.
     * To ensure that all the object can be shown in the grid
     * @return objectSize
     */
    public double calculateObjectSize(){
        if (paneWidth / row >= paneHeight / col) {
            return paneHeight / col;
        } else {
            return paneWidth / row;
        }
    }

    /**
     * Calculate the padding of game grid to ensure that the game grid is at the center of the pane
     * @return padding
     */
    public double calculatePadding(){
        double size = calculateObjectSize();
        if (paneWidth / row >= paneHeight / col) {
           return  (paneWidth - size*row)/2;
        } else {
            return (-1)*(paneHeight - size*col)/2;
        }
    }





}
