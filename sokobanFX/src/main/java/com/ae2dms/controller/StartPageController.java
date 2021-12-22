package com.ae2dms.controller;
import com.ae2dms.Main;
import com.ae2dms.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.*;

/**
 * This controller control the start page
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-01 18:42
 **/
public class StartPageController {
    /**
     * The primary stage
     */
    private Stage primaryStage;

    /**
     * The theme name of current theme.
     * Default is "theme-crayon"
     */
    public String themeName;

    /**
     * The game type of current initialized game
     */
    private String gameType;

    /**
     * The button continue. It will trigger the saved game
     */
    @FXML
    private Button contBtn;

    @FXML
    private Label themeText;

    @FXML
    private Label difficultyText;

    /**
     * The main gameController, used to start the game
     */
    MainGameController mainGameController;

    /**
     * Initialize the continue button.
     * If the saved file does not exists, the button should be disabled.
     */
    @FXML
    public void initialize() {
        File SavedFile = new File("src/main/resources/level/SavedGame.skb");
        if (!SavedFile.exists()) {
            contBtn.setDisable(true);
        }

        difficultyText.setText(Main.difficulty);
        themeText.setText(Main.themeName);
    }

    /**
     * This method is for user clicked MenuItem loadGame, and initialize game with customize game
     * @param event
     * @throws IOException
     */
    @FXML
    private void loadGame(javafx.event.ActionEvent event) throws IOException {
        gameType = "customize";
        jumpToGamePage(event);
    }

    /**
     * This method is for user clicked new game, and initialize game with new game
     * @param event
     * @throws IOException
     */
    @FXML
    private void newGame(javafx.event.ActionEvent event) throws IOException {
        gameType = "new";
        jumpToGamePage(event);
    }

    /**
     * This method is for user clicked the entertain game, and initialize the game with entertain type
     * @param event
     * @throws IOException
     */
    @FXML
    private void entertainGame(javafx.event.ActionEvent event) throws IOException {
        gameType = "entertain";
        jumpToGamePage(event);
    }

    /**
     * This method is for user clicked continue game, and initialize game with continue game
     * @param event
     * @throws IOException
     */
    @FXML
    private void contGame(javafx.event.ActionEvent event) throws IOException {
        gameType = "continue";
        jumpToGamePage(event);
    }

    /**
     * This method jump to game page, and call the method {@code gameStart} in the {@code MainGameController}
     * @param event
     * @throws IOException
     */
    @FXML
    private void jumpToGamePage(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainGame-layout.fxml"));
        Parent root = loader.load();
        primaryStage = (Stage) contBtn.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        mainGameController = loader.getController();
        mainGameController.gameStart(primaryStage,gameType);
    }


    /**
     * This method show the ranking list in a separated stage.
     * It might be called when the user press the button {@code ranking list}, or the user go back to the start game page after finishing the game
     * @throws IOException
     */
    @FXML
    public void showRankingList() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/RankingList-layout.fxml"));
        Parent root = loader.load();
        Stage rankStage = new Stage();
        rankStage.setScene(new Scene(root));
        rankStage.show();
    }


    /**
     * This method show the about page of the game.
     * It show a new stage, and the current stage and the new stage have no relationship,
     * which means the current stage can still be operated if the new stage is not closed
     * @throws IOException
     */
    public void showAbout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AboutGame-layout.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method turn on/off the music.
     * As the gameMusic is of Singleton design pattern, it should switch the status of the music.
     * If the music is turned on, then it should be turned off after this method executed, and vice-versa.
     */
    public void toggleMusic() {
        GameMusic gameMusic = GameMusic.getGameMusic();
        gameMusic.switchMusicStatus();
    }

    /**
     * This method show the theme choice of the game.
     * It show a separated stage, and what the choice has been made will change the theme immediately
     * @param event
     * @throws IOException
     */
    @FXML
    private void showThemeChoice(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ThemeChoice-layout.fxml"));
        Parent root = loader.load();
        Stage themeChoiceStage = new Stage();
        themeChoiceStage.setScene(new Scene(root));
        themeChoiceStage.showAndWait();
        themeText.setText(Main.themeName);
    }

    /**
     * This method show the difficulty choice of the game.
     * It show a separated stage, and what the choice has been made will change the theme immediately
     * @param event
     * @throws IOException
     */
    @FXML
    private void showDifficultyChoice(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/DifficultyChoice-layout.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        difficultyText.setText(Main.difficulty);
    }

    /**
     * This method show the tutorial of the game.
     * It show a separated stage, and what the choice has been made will change the theme immediately
     * @throws IOException
     */
    @FXML
    private void showTutorial() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/tutorial-scene/Tutorial1-layout.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method bind with FXML element MenuItem. It will exit the program.
     *
     * The status code of exiting is 0.
     */
    public void closeGame() {
        System.exit(0);
    }


}