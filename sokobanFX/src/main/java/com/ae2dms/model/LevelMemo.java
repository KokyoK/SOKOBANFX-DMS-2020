package com.ae2dms.model;

import java.io.*;
import java.util.Stack;

/**
 * @program: sokobanFX
 * @description: this class is to memorize each step of the game
 * @author: Yuting He
 * @create: 2020-11-17 12:37
 **/
public class LevelMemo {
    /**
     * The stack for store the levels
     */
    Stack<Level> levelStack = new Stack<>();

    /**
     * The stack for store the moves count
     */
    Stack<Integer> moveStack = new Stack<>();

    /**
     * Get the instance of level memo
     */
    private static LevelMemo instance=new LevelMemo();

    /**
     * @getter
     * Get the  {@code levelStack}
     * @return levelStack
     */
    public Stack<Level> getLevelStack() {
        return levelStack;
    }

    /**
     * Add the memo into the {@code levelMemo}
     * @param currentLevel
     * @param movesCount
     */
    public void addMemo(Level currentLevel, int movesCount){
        Object objectLevel = deepClone(currentLevel);
        levelStack.push((Level) objectLevel);
        moveStack.push(movesCount);
    }

    /**
     * clear the memo stack
     */
    public void clearMemo(){
        while(!levelStack.isEmpty()){
            levelStack.pop();
        }
        while(!moveStack.isEmpty()){
            moveStack.pop();
        }
    }

    /**
     * check if the stack is empty
     * @return
     */
    public Boolean isEmpty(){
        return levelStack.isEmpty();
    }

    /**
     * Fetch the previous memo of stack
     * @return
     */
   public Level fetchPreMemo(){
        return levelStack.pop();
    }

    /**
     * Fetch the initial moves count by pop all the element out of the stack
     * @return
     */
    public int fetchInitialMove(){
        int initialCount = -1;
        while(!moveStack.isEmpty()){
            initialCount = moveStack.pop();
        }
        return initialCount;
    }
    /**
     * This method implement deep copy of an object
     * @param oldObj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object deepClone(Object oldObj) {
        Object obj =null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(oldObj);
            out.flush();
            out.close();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            obj =in.readObject();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }

    /**
     * Get the level memo (singleton)
     * @return
     */
    public static LevelMemo getLevelMemo(){
        if (instance == null) {
            synchronized (GameMusic.class) {
                if (instance == null) {
                    instance = new LevelMemo();
                }
            }
        }
        return instance;
    }


}

