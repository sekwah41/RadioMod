package com.sekwah.radiomod.music;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by on 04/08/2016.
 *
 *
 *
 * @author sekwah41
 */
public class MusicManager {

    private int pausedOnFrame = 0;

    public final float framesPerTick = 1180 / 30f / 20f;

    public Map<String,MusicSource> radioSources = new HashMap<String,MusicSource>();

    public MusicManager(){
    }

    public void playAssetsSound(String songName){
        Thread musicPlayer = new Thread(new MusicRunnable(songName));
        musicPlayer.start();
    }

    public void playStreamUrl(String streamUrl){
        Thread musicPlayer = new Thread(new RadioRunnable(streamUrl));
        musicPlayer.start();
    }

    public void stopAllPlayers() {
        for(MusicSource source: this.radioSources.values()){
            source.stopMusic();
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
                AdvancedPlayer player = new AdvancedPlayer(resourseStream);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        pausedOnFrame = event.getFrame();
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
                        pausedOnFrame = event.getFrame();
                    }
                });
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

}
