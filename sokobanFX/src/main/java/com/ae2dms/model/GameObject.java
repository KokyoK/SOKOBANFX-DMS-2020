package com.ae2dms.model;

/**
 * This class is the game object。
 * The game object has two type, enum and symbol. These two type can convert from each other.
 *
 * It contains two players and can manipulate two players' move.
 * It extends from {@code GameEngine}
 * @Author: Yuting He
 * @Version: 1.0
 */
public enum GameObject {
    WALL('W'),
    FLOOR(' '),
    CRATE('C'),
    DIAMOND('D'),
    KEEPER('S'),
    FRIEND('F'),
    CRATE_ON_DIAMOND('O'),
    KEEPER_ON_DIAMOND('U'),
    FRIEND_ON_DIAMOND('V'),
    DEBUG_OBJECT('=');

    /**
     * The symbol of game object
     */
    public final char symbol;

    /**
     * The constructor of game object.
     * @param symbol
     */
    GameObject(final char symbol) {
        this.symbol = symbol;
    }

    /**
     *
     * @param c （game object symbol
     * @return the game object (default is WALL)
     */
    public static GameObject fromChar(char c) {
        for (GameObject t : GameObject.values()) {
            if (Character.toUpperCase(c) == t.symbol) {
                return t;
            }
        }
        return WALL;
    }

    /**
     * Get the symbol in string format
     * @return
     */
    public String getStringSymbol() {
        return String.valueOf(symbol);
    }

    /**
     * Get the symbol in char format
     * @return
     */
    public char getCharSymbol() {
        return symbol;
    }
}
