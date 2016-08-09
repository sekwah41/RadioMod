package com.sekwah.radiomod.music;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.music.player.CustomPlayer;
import com.sekwah.radiomod.music.song.*;
import com.sekwah.radiomod.util.FastFourierTransform;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    private boolean playingRadioStream = false;
    private List<double[]> frequencyData = new ArrayList<double[]>();
    private int lastFrameFrequencyAnalizedOn = 0;
	private String radioStationURL;

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
        this.setPlayingRadioStream(false);
        return true;
    }

    public void startFromTrackData(TrackingData trackingData, boolean fromTicks) {
        switch (trackingData.type){
            case TrackingData.BUILTIN:
                this.playBuiltInSongCollection(Integer.parseInt(trackingData.source), trackingData.currentTick, fromTicks);
                break;
            case TrackingData.ONLINE:
                this.playStreamUrl(trackingData.source,trackingData.currentTick, fromTicks);
                break;
            case TrackingData.STREAM:
                this.playStreamUrl(trackingData.source);
                break;
        }
    }

    public void playPrivateSongCollection(int songID){
        this.playPrivateSongCollection(songID, 0, false);
    }

    public void playPrivateSongCollection(int songID, int frame, boolean ticks){
        if(songID > SongPrivate.privateSongCollection.size() || songID < 0){
            return;
        }
        this.currentSong = SongPrivate.privateSongCollection.get(songID);
        this.stopMusic();
        Thread musicPlayer = null;
        try {
            musicPlayer = new Thread(new MusicRunnable(
                    new FileInputStream(
                            new File(FileManager.privateSongsDir.getAbsolutePath() + File.separator + SongPrivate.privateSongCollection.get(songID).getFileName()))
                    , frame, ticks));
            musicPlayer.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        if(getIsPlaying()){
            this.player.setVolume(volume);
        }
        this.volume = volume;
    }

    public void playBuiltInSongCollection(int songID){
        this.playBuiltInSongCollection(songID, 0, false);
    }

    public void playBuiltInSongCollection(int songID, int frame, boolean ticks){
        if(songID > SongBuiltIn.builtInSongCollection.size() || songID < 0){
            return;
        }
        this.currentSong = SongBuiltIn.builtInSongCollection.get(songID);
        this.stopMusic();
        String songLocation = SongBuiltIn.builtInSongCollection.get(songID).getFileName();
        Thread musicPlayer = new Thread(new MusicRunnable(this.getClass().getResourceAsStream("/assets/radiomod/sounds/songs/" + songLocation + ".mp3"), frame, ticks));
        musicPlayer.start();
    }

    public void playStreamUrl(String streamUrl){
        this.playStreamUrl(streamUrl,0, false);
    }

    public void playStreamUrl(String streamUrl, int frame, boolean ticks){
        this.stopMusic();
        Thread musicPlayer = null;
        try {
            musicPlayer = new Thread(new MusicRunnable(new URL(streamUrl).openConnection().getInputStream(), frame, ticks));
            musicPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.radioStationURL = streamUrl;
        
        this.setPlayingRadioStream(true);
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
            //this.calculateFrequencyData();
            this.lastFrameFrequencyAnalizedOn = this.player.getFrame();
        }
        
        this.calculateFrequencyData();

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
        return (this.player != null && this.player.getRawData() != null) ? this.player.getRawData().length : 0;
    }

    public int getSampleRatePow2() {
        int sampleRate = getSampleRate();
        sampleRate = sampleRate >= 2048 ? 2048 : sampleRate >= 1028 ? 1028 : 0;
        return sampleRate;
    }


    class MusicRunnable implements Runnable {

        private final boolean isTicks;

        private InputStream stream;

        private final int frames;

        public MusicRunnable(InputStream stream, int startFrame, boolean isTicks){
            this.stream = stream;
            this.frames = startFrame;
            this.isTicks = isTicks;
        }

        @Override
        public void run() {

            try {
                player = new CustomPlayer(stream, volume);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        //currentFrame = event.getFrame();
                        player = null;
                    }
                });
                if(isTicks){
                    int frame = (int) ((float) (frames) / 20f * 1000f / player.getFirstFrameHeader().ms_per_frame());
                    player.playFrom(frame);
                }
                else{
                    player.playFrom(frames);
                }
                RadioMod.logger.info(volume);
            } catch (JavaLayerException e) {
                player = null;
                e.printStackTrace();
            } catch (IOException e) {
                player = null;
                e.printStackTrace();
            }
        }
    }

    public Song getCurrentSong() {
        return this.currentSong;
    }

	public boolean isPlayingRadioStream() {
		return playingRadioStream;
	}

	public void setPlayingRadioStream(boolean playingRadioStream) {
		System.out.println("Setting play readio stream to " + playingRadioStream);
		this.playingRadioStream = playingRadioStream;
	}

	public String getRadioStationURL() {
		return this.radioStationURL;
	}
}
