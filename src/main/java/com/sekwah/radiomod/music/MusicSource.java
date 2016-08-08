package com.sekwah.radiomod.music;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.music.player.CustomPlayer;
import com.sekwah.radiomod.music.song.Song;
import com.sekwah.radiomod.music.song.SongBuiltIn;
import com.sekwah.radiomod.music.song.SongPrivate;
import com.sekwah.radiomod.util.FastFourierTransform;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import scala.actors.threadpool.Arrays;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by on 05/08/2016.
 *
 * The code to be stored by the tile entity.
 *
 * @author sekwah41
 */
public class MusicSource {

    private TileEntityRadio tileEntity;

    //private int currentFrame;

    private Song currentSong;

    private Thread currentThread;

    private int pauseFrame = 0;

    private States songState = States.STOPPED;

    private float volume = 0.4f;

    public enum States {
        PAUSED,
        STOPPED,
        PLAYING
    }

    /**
     * Used for playing the current song
     */
    private CustomPlayer player = null;
    private List<double[]> frequencyData = new ArrayList<double[]>();
    private int lastFrameFrequencyAnalizedOn = 0;
    
    public MusicSource(){
    	this.volume = 0.4f;
    }

    /**
     *
     * @return returns true if the music stops or is already stopped.
     */
    public boolean stopMusic(){
        if(player != null && !player.getClosed()){
            player.stop();
        }
        songState = States.PAUSED;
        this.lastFrameFrequencyAnalizedOn = -1;
        return true;
    }

    public void playPrivateSongCollection(int songID){
        this.playPrivateSongCollection(songID, 0);
    }

    public void playPrivateSongCollection(int songID, int frame){
        if(songID > SongPrivate.privateSongCollection.size() || songID < 0){
            return;
        }
        this.currentSong = SongPrivate.privateSongCollection.get(songID);
        this.stopMusic();
        Thread musicPlayer = new Thread(new PrivateMusicRunnable(FileManager.privateSongsDir.getAbsolutePath() + File.separator + SongPrivate.privateSongCollection.get(songID).getFileName(), frame));
        musicPlayer.start();
    }

    public void playAssetsSound(String songName, int frame){
        this.stopMusic();
        Thread musicPlayer = new Thread(new MusicRunnable(songName, frame));
        musicPlayer.start();
    }

    public void setVolume(float volume) {
        if(getIsPlaying()){
            this.player.setVolume(volume);
        }
        this.volume = volume;
    }

    public void playBuiltInSongCollection(int songID){
        this.playBuiltInSongCollection(songID, 0);
    }

    public void playBuiltInSongCollection(int songID, int frame){
        if(songID > SongBuiltIn.builtInSongCollection.size() || songID < 0){
            return;
        }
        this.currentSong = SongBuiltIn.builtInSongCollection.get(songID);
        this.stopMusic();
        Thread musicPlayer = new Thread(new MusicRunnable(SongBuiltIn.builtInSongCollection.get(songID).getFileName(), frame));
        musicPlayer.start();
    }

    public void playStreamUrl(String streamUrl){
        this.playStreamUrl(streamUrl,0);
    }

    public void playStreamUrl(String streamUrl, int frame){
        this.stopMusic();
        Thread musicPlayer = new Thread(new RadioRunnable(streamUrl));
        musicPlayer.start();
    }

    public int getCurrentFrame(){
        return this.player.getFrame();
    }

    public boolean getIsPlaying(){
        return player != null;
    }

    public CustomPlayer getPlayer(){
        return player;
    }
    
    public double[] getFrequencyData(int index) {
    	if(this.player != null && this.player.getFrame() > this.lastFrameFrequencyAnalizedOn) {
    		this.calculateFrequencyData();
    		this.lastFrameFrequencyAnalizedOn = this.player.getFrame();
    	}
    	
    	return this.frequencyData.get(index);
    }
    
    public double[] getFrequencyData() {
    	return this.getFrequencyData(this.frequencyData.size()-1);
    }
    
    public boolean hasFrequencyData() {
    	return this.frequencyData.size() > 0;
    }
    
    public void calculateFrequencyData() {
    	if(this.player == null) return;
    	int sampleRate = this.getSampleRatePow2();
    	if(sampleRate == 0) return;
    	
    	while(this.frequencyData.size() > 3) this.frequencyData.remove(0);
    	this.frequencyData.add(FastFourierTransform.transform(Arrays.copyOf(this.player.getRawData(), sampleRate), true));
    }
    
    public int getSampleRate() {
    	return this.player.getRawData() != null ? this.player.getRawData().length : 0;
    }
    
    public int getSampleRatePow2() {
    	int sampleRate = getSampleRate();
    	sampleRate = sampleRate >= 2048 ? 2048 : sampleRate >= 1028 ? 1028 : 0;
    	return sampleRate;
    }
    
    class PrivateMusicRunnable implements Runnable {

        private final String location;

        private final int startFrame;

        public PrivateMusicRunnable(String location, int startFrame){
            this.location = location;
            this.startFrame = startFrame;
        }

        @Override
        public void run() {

            RadioMod.logger.info(location);

            RadioMod.logger.info(FileManager.privateSongsDir.getAbsolutePath());

            try {
                InputStream resourseStream = new FileInputStream(new File(location));

                Bitstream bitstream = new Bitstream(resourseStream);

                player = new CustomPlayer(resourseStream, volume);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        //currentFrame = event.getFrame();
                        player = null;
                    }
                });
                player.playFrom(startFrame);
            } catch (JavaLayerException e) {
                player = null;
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                player = null;
                e.printStackTrace();
            }
        }
    }

    class MusicRunnable implements Runnable {

        private final String songName;
        private final int startFrame;

        public MusicRunnable(String songName, int frame){
            this.songName = songName;
            this.startFrame = frame;
        }



        @Override
        public void run() {
            InputStream resourseStream = this.getClass().getResourceAsStream("/assets/radiomod/sounds/songs/" + songName + ".mp3");

            Bitstream bitstream = new Bitstream(resourseStream);

            try {
                player = new CustomPlayer(resourseStream, volume);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        //currentFrame = event.getFrame();
                        player = null;
                    }
                });
                player.playFrom(startFrame);
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
                player = new CustomPlayer(resourseStream, volume);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        // currentFrame = event.getFrame();
                        player = null;
                    }
                });
                player.play();
            } catch (JavaLayerException e) {
                player = null;
                e.printStackTrace();
            }
        }
    }

	public Song getCurrentSong() {
		return this.currentSong;
	}
}
