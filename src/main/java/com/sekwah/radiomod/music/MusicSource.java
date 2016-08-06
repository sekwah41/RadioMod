package com.sekwah.radiomod.music;

import com.sekwah.radiomod.music.player.CustomPlayer;
import com.sekwah.radiomod.music.song.Song;

/**
 * Created by on 05/08/2016.
 *
 * The code to be stored by the tile entity.
 *
 * @author sekwah41
 */
public class MusicSource {

    private int currentFrame;

    private Song currentSong;

    private boolean isPlaying = false;

    private CustomPlayer player = null;

    public MusicSource(){

    }

    public boolean stopMusic(){
        if(isPlaying && player != null){
            player.stop();
        }
        return true;
    }

    public boolean playLocalSong(String songName){
      return true;
    }

    public boolean playBuiltInSong(String songName){
      return true;
    }

    public boolean playInternetSong(String mp3StreamURL){
      return true;
    }

    public boolean getIsPlaying(){
        return isPlaying;
    }

}
