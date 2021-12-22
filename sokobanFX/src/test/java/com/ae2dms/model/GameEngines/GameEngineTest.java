package com.ae2dms.model.GameEngines;

import com.ae2dms.model.GameObject;
import javafx.scene.input.KeyCode;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {
    private GameEngine gameEngine;

    @Test
    public void testNormalMovePush() throws IOException, ClassNotFoundException {
        File in = new File("src/test/resources/testNormal.skb");
        this.gameEngine = new GameEngineNormal(in,true);
        Point temp = gameEngine.getCurrentLevel().getKeeperPosition();
        Point previousKeeperPosition = new Point(temp.x, temp.y);
        gameEngine.handleKey(KeyCode.LEFT);
        Point currentKeeperPosition = gameEngine.getCurrentLevel().getKeeperPosition();
        assertEquals(previousKeeperPosition.y - 1, currentKeeperPosition.y);
        Point currentCratePosition = new Point(currentKeeperPosition.x, (currentKeeperPosition.y) - 1);
        assertEquals(GameObject.FLOOR, translatePosition(previousKeeperPosition));
        assertEquals(GameObject.KEEPER, translatePosition(currentKeeperPosition));
        assertEquals(GameObject.CRATE, translatePosition(currentCratePosition));
    }

    @Test
    public void testEntertainMoveFriend() throws IOException, ClassNotFoundException {
        File in = new File("src/test/resources/testEntertain.skb");
        this.gameEngine = new GameEngineEntertain(in,true);
        Point temp = gameEngine.getCurrentLevel().getFriendPosition();
        Point previousFriendPosition = new Point(temp.x, temp.y);
        gameEngine.handleKey(KeyCode.W);
        Point currentFriendPosition = gameEngine.getCurrentLevel().getFriendPosition();
        assertEquals(previousFriendPosition.y , currentFriendPosition.y);
        assertEquals(GameObject.FRIEND, translatePosition(previousFriendPosition));
        assertEquals(GameObject.FRIEND, translatePosition(currentFriendPosition));
    }


    public GameObject translatePosition(Point position){
        return gameEngine.getCurrentLevel().objectsGrid.getGameObjectAt(position);
    }



}