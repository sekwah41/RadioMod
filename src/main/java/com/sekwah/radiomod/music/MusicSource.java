package com.sekwah.radiomod.music;

import com.sekwah.radiomod.blocks.RadioBlock;
import com.sekwah.radiomod.music.player.CustomPlayer;
import com.sekwah.radiomod.music.song.Song;
import com.sekwah.radiomod.music.song.SongPrivate;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by on 05/08/2016.
 *
 * The code to be stored by the tile entity.
 *
 * @author sekwah41
 */
public class MusicSource {

    private final RadioBlock radioBlock;

    private int currentFrame;

    private Song currentSong;

    private boolean isPlaying = false;

    private Thread currentThread;

    /**
     * Used for playing the current song
     */
    private CustomPlayer player = null;

    public MusicSource(RadioBlock block){
        this.radioBlock = block;
    }

    /**
     *
     * @return returns true if the music stops or is already stopped.
     */
    public boolean stopMusic(){
        if(isPlaying && player != null){
            player.stop();
        }
        return true;
    }

    public void playSongCollection(int songID){
        if(songID > SongPrivate.privateSongCollection.size() || songID < 0){
            return;
        }
        this.stopMusic();
        Thread musicPlayer = new Thread(new MusicRunnable(FileManager.privateSongsDir.getAbsolutePath() + File.pathSeparator + SongPrivate.privateSongCollection.get(songID).getFileName()));
        musicPlayer.start();
    }

    public void playAssetsSound(String songName){
        this.stopMusic();
        Thread musicPlayer = new Thread(new MusicRunnable(songName));
        musicPlayer.start();
    }

    public void playStreamUrl(String streamUrl){
        this.stopMusic();
        Thread musicPlayer = new Thread(new RadioRunnable(streamUrl));
        musicPlayer.start();
    }

    public boolean getIsPlaying(){
        return isPlaying;
    }


    class MusicRunnable implements Runnable {

        private final String songName;

        public MusicRunnable(String songName){
            this.songName = songName;
        }

        @Override
        public void run() {
            InputStream resourseStream = this.getClass().getResourceAsStream("/assets/radiomod/sounds/songs/" + songName + ".mp3");

            Bitstream bitstream = new Bitstream(resourseStream);

            try {
                AdvancedPlayer player = new AdvancedPlayer(resourseStream);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        currentFrame = event.getFrame();
                        isPlaying = false;
                    }
                });
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    class RadioRunnable implements Runnable {

        private final String streamString;

        public RadioRunnable(String streamString){
            this.streamString = streamString;
        }

        @Override
        public void run() {
            InputStream resourseStream = null;
            try {
                resourseStream = new URL(streamString).openConnection().getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitstream bitstream = new Bitstream(resourseStream);

            try {
                AdvancedPlayer player = new AdvancedPlayer(resourseStream);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        currentFrame = event.getFrame();
                        isPlaying = false;
                    }
                });
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }



}
