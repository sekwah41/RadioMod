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

    public Map<String,Float> sourceDistances = new HashMap<String,Float>();

    public MusicManager(){
    }

    public void stopAllPlayers() {
        for(MusicSource source: this.radioSources.values()){
            source.stopMusic();
        }
    }

}
