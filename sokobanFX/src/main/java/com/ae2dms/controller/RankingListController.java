package com.ae2dms.controller;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;

import com.ae2dms.model.PlayerScore;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * This class control the rank list stage.
 * It read from file, store the data into {@code rawRankData}, sort them into ascending order, and display to the window.
 * Observe whether the user has changed the choice box, it should appropriately display the corresponding map set.
 * @program: sokobanFX
 * @author: Yuting He
 * @create: 2020-11-10 10:15
 **/
public class RankingListController {
    /**
     * The pane show the rank data
     */
    @FXML
    private Pane rankPane;

    /**
     * The ok button to close the stage
     */
    @FXML
    private Button okBtn;

    /**
     * The choice box for user to choose map set
     */
    @FXML
    private ChoiceBox setChoice;

    /**
     * The arrayList for rank file name storage
     */
    private ArrayList<String> rankFileList = new ArrayList<>();

    /**
     * The list to store the all rank data for different players
     */
    private ArrayList<ArrayList<PlayerScore>> rawRankData = new ArrayList<>();

    /**
     * This method read data from file,sort the data, and show the first rank.
     * Add observer to the choice box.
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {
        String path = "src/main/resources/rank";
        getFile(path);
        readRankData();
        showRank(0);
        setChoice.setItems(FXCollections.observableArrayList(rankFileList));
        setChoice.setValue(rankFileList.get(0));
        setChoice.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_val, Number new_val)->{
                    showRank(new_val.intValue());
                }
        );
    }


    /**
     * This method read data from file and sort data in
     * and store into an ArrayList of Arraylist
     * @throws FileNotFoundException
     */
    private void readRankData() throws FileNotFoundException {
        for(int i = 0; i < rankFileList.size(); i++){
            String curFilePath = "src/main/resources/rank/"+rankFileList.get(i)+"Rank.txt";
            FileReader fr = new FileReader(curFilePath);
            ArrayList<PlayerScore> rawRank = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(fr)) {
                while (true) {      // this loop read every line in the rank file
                    String line = reader.readLine();
                    String playerName = "";
                    int movesCount = 999;
                    if (line == null || !line.contains(":") ) {   //empty line
                        break;
                    }else{
                        String contents[] = line.split(":", 2);
                        movesCount = Integer.parseInt(contents[0]);
                        playerName = contents[1];
                    }
                    PlayerScore curPlayer = new PlayerScore(movesCount,playerName);
                    rawRank.add(curPlayer);
                }
            } catch (IOException e) {
                System.out.println("Error:" + e );
            }
            rawRankData.add(sortRank(rawRank));
        }
    }

    /**
     * This method show the top 10 rank of the selected map set.
     * If the rank count is less than 10, then it should show unknown in the page.
     * @param idx
     */
    private void showRank (int idx) {
        ArrayList<Label> countLabels= new ArrayList<>();
        ArrayList<Label> playerLabels= new ArrayList<>();
        for (int i = 0; i < 10; i++){
            countLabels.add((Label) rankPane.lookup("#top"+(i+1)+"count"));
            playerLabels.add((Label) rankPane.lookup("#top"+(i+1)+"player"));
        }
        ArrayList<PlayerScore> curList = rawRankData.get(idx);
        System.out.println(curList.size());
        for(int i = 0; i < 10; i++){
            countLabels.get(i).setText("unknown");
            playerLabels.get(i).setText("unknown");
        }

        for (int i = 0; i < (curList.size()<10?curList.size():10); i++){
            countLabels.get(i).setText(String.valueOf(curList.get(i).getMovescount()));
            playerLabels.get(i).setText(curList.get(i).getPlayerName());
        }
    }

    /**
     * This method sort array list of string in ascending order and return the sorted rank
     * @param rawRank
     */
    private ArrayList<PlayerScore> sortRank (ArrayList<PlayerScore> rawRank){
        Collections.sort(rawRank, Comparator.comparingInt(PlayerScore::getMovescount)
        );
        return rawRank;
    }


    /**
     * This method get the file in the rank data directory iteratively.
     * The file named "SavedGame.skb" should be excluded from the list, as it only store the saved game.
     * @param path
     */
    private void getFile(String path){
        File file = new File(path);
        File[] array = file.listFiles();
        for(int i=0;i<array.length;i++)
        {
            if(array[i].isFile())
            {
                String curFileName = array[i].getName();
                if (!curFileName.substring(0,1).equals(".") && !curFileName.equals("SavedGame.skb")){
                    rankFileList.add(curFileName.substring(0,curFileName.length()-8));
                }
            }
        }
    }

    /**
     * This method close the current stage
     */
    @FXML
    private void closeWindow(){
        Stage stage = (Stage)okBtn.getScene().getWindow();
        stage.close();
    }


}
