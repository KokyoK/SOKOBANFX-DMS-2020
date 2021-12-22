package com.ae2dms;

import com.ae2dms.model.GameEngines.GameEngine;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * This class the start of the program, and extends the {@code Application}.
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-10-31 20:14
 */
public class Main extends Application {
    /**
     * Primary stage to handle
     */
    public static Stage primaryStage;

    /**
     * Root of stages
     */
    public static Parent root;

    /**
     * Theme name global
     */
    public static String themeName = "theme-crayon";

    /**
     * Difficulty global
     */
    public static String difficulty = "simple";


    /**
     * launch the game
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * This method starts the application, and handle the
     * contains "new game"  "continue"
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/StartPage-layout.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.resizableProperty().set(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SokobanFX");
        primaryStage.show();
        Main.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }
}
