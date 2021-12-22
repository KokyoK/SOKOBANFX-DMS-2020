package com.ae2dms.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ae2dms.model.GameEngines.GameEngine.isDebugActive;

/**
 * This controller controls the victory message.
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-18 20:34
 **/
public class VictoryController  {
    /**
     * The victory message label
     */
    @FXML
    private Label vicMsg;

    /**
     * The ok button
     */
    @FXML
    private Button okBtn;

    /**
     * The player input name field
     */
    @FXML
    private TextField nameField;

    /**
     * The alert message.
     * Hide at first, when user does not enter the username or enter with incorrect format, this message will show
     */
    @FXML
    private Label alertMsg;

    /**
     * The movesCount field
     */
    private int movesCount;

    /**
     * The map set name field
     */
    private String mapSet;

    /**
     * The primary stage
     */
    private Stage primaryStage;

    /**
     * This method is called when initialize the game.
     * It set the victory message based on the map set name and movesCount
     * @param movesCount
     * @param mapSet
     */
    public void setVicMsg(int movesCount, String mapSet){
        this.mapSet = mapSet;
        this.movesCount=movesCount;
        System.out.println("hhh:"+mapSet);
        vicMsg.setText("Congrats! You complete game "+ mapSet.substring(0,mapSet.length()-4) + " in " + movesCount + " moves");
    }

    /**
     * This message is called when user clicked ok.
     * It judges whether the user input is of correct format.
     * @throws IOException
     */
    @FXML
    private void clickOK() throws IOException {
        if (nameField.getText().equals("")){
            alertMsg.setText("Please Enter your name!");
            return;
        }else if(nameField.getText().contains(":")){
            alertMsg.setText("Player name cannot contain ':' ! ");
            return;
        }else{
            alertMsg.setText("");
        }

        try {
            storePlayerData();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeWindow();
            showStartPageAndRankList();
        }

    }

    /**
     * This method show start page and ranking list.
     * It firstly load the start page, and get the start page controller.<br>
     * Use thi s controller to show ranking list based on start page.
     * @throws IOException
     */
    @FXML
    private void showStartPageAndRankList() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/StartPage-layout.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        StartPageController startPageController = loader.getController();
        startPageController.showRankingList();
    }

    /**
     * This method close the current window.
     * It gets stage from the ok button and close the stage.
     */
    @FXML
    private void closeWindow()  {
        Stage stage = (Stage)okBtn.getScene().getWindow();
        stage.close();

    }

    /**
     * This method stores the player's rank data into a specific file.
     * If this map set is firstly played, then it should create a file.<br>
     * This method uses buffer writer to write into the file.
     * @throws IOException
     */
    private void storePlayerData() throws IOException {
        String rankFileName = mapSet.replace(".skb","") + "Rank.txt";
        File rankFile = new File("src/main/resources/rank/"+rankFileName);
        if (!rankFile.exists()) {   // if the file does not exist
            rankFile.createNewFile(); // create one
        }
        if (rankFile.exists()) {
            System.out.println("file exists");
            FileWriter fw = new FileWriter(rankFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(movesCount+":"+nameField.getText()+"\n");
            bw.flush();
            bw.close();
            fw.close();
            System.out.println("rank saved successfully!");
        } else {
            if (isDebugActive()) {
                System.out.println("the file does not exists");
            }
        }
    }


    /**
     * This method saves the primary stage for later jumping to the start page.
     * @param primaryStage
     */
    public void savePrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


}
