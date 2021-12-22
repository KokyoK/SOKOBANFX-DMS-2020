package com.ae2dms.model;

/**
 * @program: sokobanFX
 * @description: this class is used when player finish the game, and store data
 * @author: Yuting He
 * @create: 2020-11-18 10:48
 **/
public class PlayerScore {
    /**
     * The move count of current level
     */
    int movescount;

    /**
     * The player name
     */
    String playerName;

    /**
     * The constructor of Player score
     * @param movescount
     * @param playerName
     */
    public PlayerScore(int movescount, String playerName){
        this.movescount = movescount;
        this.playerName = playerName;
    }

    /**
     * Get moves count
     * @getter
     * @return
     */
    public int getMovescount() {
        return movescount;
    }

    /**
     * Get player's name
     * @getter
     * @return
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Show the player score and name
     * @return
     */
    @Override
    public String toString() {
        return "PlayerScore{" +
                "movescount=" + movescount +
                ", playerName='" + playerName + '\'' +
                '}'+"\n";
    }



}
