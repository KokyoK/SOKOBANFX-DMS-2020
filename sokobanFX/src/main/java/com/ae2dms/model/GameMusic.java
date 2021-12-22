package com.ae2dms.model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * @program: sokobanFX
 * @description: this class designed with singleton pattern (lazy Double Check Singleton)
 * @author: Yuting He
 * @create: 2020-11-14 17:20
 **/
public class GameMusic {

    /**
     * The music clip
     */
    private Clip clip;

    /**
     * The game music instance
     */
    private static GameMusic instance=null;

    /**
     * Get the clip status.
     * If the clip is null or stopped, return false.
     * Else, return true.
     *
     * @return status
     */
    public Boolean getClipStatus() {
        if (clip == null){
            return false;
        }
       if (clip.isActive()){
           return true;
       }else{
           return false;
       }
    }

    /**
     * @setter set the clip
     * @param clip
     */
    public void setClip(Clip clip) {
        this.clip = clip;
    }

    /**
     * Default constructor of gameMusic
     */
    private GameMusic() {};

    /**
     * Get the game music instance (singleton design pattern)
     * @return
     */
    public static GameMusic getGameMusic(){
        if (instance == null) {
            synchronized (GameMusic.class) {
                if (instance == null) {
                    instance = new GameMusic();
                }
            }
        }
        return instance;
    }

    /**
     * This method switch music status.
     * If the music is null or turned off, turn on the music.
     * Else, turn off the music
     */
    public void switchMusicStatus(){
        String musicLocation = "src/main/resources/music/puzzle_theme.wav";
        try
        {
            File musicPath = new File(musicLocation);

            if(musicPath.exists())
            {
                if(clip == null){           // first time play
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                    clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }else if(clip.isActive()){      // turn off the music
                    clip.stop();
                }else{                      // turn on the music
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
