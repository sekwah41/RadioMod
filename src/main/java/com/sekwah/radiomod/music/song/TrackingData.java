package com.sekwah.radiomod.music.song;

/**
 * Created by on 07/08/2016.
 *
 * @author sekwah41
 */
public class TrackingData {

    /**
     * builtin - just song name
     * private - song name for the private folder
     * online - url for song
     * stream - url for song
     */
    public int type;

    public static final int BUILTIN = 1;
    public static final int PRIVATE = 2;
    public static final int ONLINE = 3;
    public static final int STREAM = 4;

    /**
     * Builtin - just song name
     * Private - song name for the private folder
     * Online - url for song
     * Stream - url for song
     */
    public String source;

    // Convert the max time to max ticks.
    //public int maxFrames;

    public int currentTick;

    public int maxTicks;

    public TrackingData(int type, String source, int currentTicks){
        this.type = type;
        this.source = source;
        this.currentTick = currentTicks;
    }


}
