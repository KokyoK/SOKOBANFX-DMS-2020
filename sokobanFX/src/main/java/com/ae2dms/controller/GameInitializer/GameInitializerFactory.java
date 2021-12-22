package com.ae2dms.controller.GameInitializer;

/**
 * @program: sokobanFX
 * @author: Yuting He
 * @version: 1.0.0
 * @create: 2020-11-20 22:19
 * This class is used to generate game initializer. <br/>
 **/
public class GameInitializerFactory {

    /**
     * This method get game initializer based on game type. <br/>
     * The factory will not generate ResetGameInitializer, which should be generated directly in the controller.<br/>
     * @param gameType
     * @return specific type of game initializer
     */
    public GameInitializer getGameInitializer(String gameType){
        if(gameType == "new"){
            return new NewGameInitializer();
        }else if(gameType == "continue"){
            return new ContinueGameInitializer();
        }else if(gameType == "customize"){
            return new CustomizeGameInitializer();
        }else if(gameType == "entertain"){
            return new EntertainGameInitializer();
        }
        return null;
    }
}
