package com.sekwah.radiomod.music;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.music.player.CustomPlayer;
import com.sekwah.radiomod.music.song.Song;
import com.sekwah.radiomod.music.song.SongPrivate;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.*;
import java.net.URL;

/**
 * Created by on 05/08/2016.
 *
 * The code to be stored by the tile entity.
 *
 * @author sekwah41
 */
public class MusicSource {

    private TileEntityRadio tileEntity;

    private int currentFrame;

    private Song currentSong;

    private Thread currentThread;

    /**
     * Used for playing the current song
     */
    private CustomPlayer player = null;

    public MusicSource(TileEntityRadio tileEntity){
        this.tileEntity = tileEntity;
    }

    /**
     *
     * @return returns true if the music stops or is already stopped.
     */
    public boolean stopMusic(){
        if(player != null && !player.getClosed()){
            player.stop();
        }
        return true;
    }

    public void playSongCollection(int songID){
        if(songID > SongPrivate.privateSongCollection.size() || songID < 0){
            return;
        }
        this.stopMusic();
        Thread musicPlayer = new Thread(new PrivateMusicRunnable(FileManager.privateSongsDir.getAbsolutePath() + File.separator + SongPrivate.privateSongCollection.get(songID).getFileName()));
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
        return player != null;
    }

    class PrivateMusicRunnable implements Runnable {

        private final String location;

        public PrivateMusicRunnable(String location){
            this.location = location;
        }

        @Override
        public void run() {

            RadioMod.logger.info(location);

            RadioMod.logger.info(FileManager.privateSongsDir.getAbsolutePath());

            try {
                InputStream resourseStream = new FileInputStream(new File(location));

                Bitstream bitstream = new Bitstream(resourseStream);

                player = new CustomPlayer(resourseStream);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        currentFrame = event.getFrame();
                    }
                });
                player.play();
            } catch (JavaLayerException | FileNotFoundException e) {
                player = null;
                e.printStackTrace();
            }
        }
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
                player = new CustomPlayer(resourseStream);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        currentFrame = event.getFrame();
                    }
                });
                player.play();
            } catch (JavaLayerException e) {
                player = null;
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
                player = new CustomPlayer(resourseStream);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        currentFrame = event.getFrame();
                    }
                });
                player.play();
            } catch (JavaLayerException e) {
                player = null;
                e.printStackTrace();
            }
        }
    }



}
