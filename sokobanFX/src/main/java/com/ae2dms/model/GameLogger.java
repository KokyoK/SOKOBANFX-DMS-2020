package com.ae2dms.model;

import com.ae2dms.model.GameEngines.GameEngine;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class is the game logger
 *
 * It extends from {@code Logger}, for developers to debug
 * @Author: Yuting He
 * @Version: 1.0
 */
public class GameLogger extends Logger {

    /**
     * The game logger
     */
    private static Logger logger = Logger.getLogger("GameLogger");

    /**
     * The data format
     */
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * The calendar
     */
    private Calendar calendar = Calendar.getInstance();

    /**
     * This method initialize a new game logger
     * @throws IOException
     */
    public GameLogger() throws IOException {
        super("com.aes2dms.sokoban", null);
        File directory = new File(System.getProperty("user.dir") + "/" + "logs"); // get the logs directory
        directory.mkdirs(); // enter the next level folder (if not exists, create)

        FileHandler fh = new FileHandler(directory + "/" + GameEngine.GAME_NAME + ".log"); // create {$gamename}.log file
        logger.addHandler(fh);   // add log handler to receive log message
        SimpleFormatter formatter = new SimpleFormatter(); // format log records
        fh.setFormatter(formatter);
    }

    /**
     * This method create a formatted string
     * @param message
     * @return
     */
    private String createFormattedMessage(String message) {
        return dateFormat.format(calendar.getTime()) + " -- " + message; // get date time
    }

    /**
     * This method create info
     * @param message
     */
    public void info(String message) {
        logger.info(createFormattedMessage(message));
    }

    /**
     * This method create warning
     * @param message
     */
    public void warning(String message) {
        logger.warning(createFormattedMessage(message));
    }

    /**
     * This method create severe
     * @param message
     */
    public void severe(String message) {
        logger.severe(createFormattedMessage(message));
    }
}