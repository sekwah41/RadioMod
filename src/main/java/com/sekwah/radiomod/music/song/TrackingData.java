package com.sekwah.radiomod.music.song;

import javax.sound.midi.Track;

/**
 * Created by on 07/08/2016.
 *
 * @author sekwah41
 */
public class TrackingData {

    public TrackType type;

    /**
     * Builtin - just song name
     * Private - song name for the private folder
     * Online - url for song
     * Stream - url for song
     */
    public String source;

    // Convert the max time to max ticks.
    public int maxTicks;

    public int currentTicks;


    public enum TrackType{
        BUILTIN(0,"builtin"),
        PRIVATE(1,"private"),
        ONLINE(2,"online"),
        STREAM(3,"stream");

        private final int index;

        private final String name;

        private TrackType(int index, String name){
            this.index = index;
            this.name = name;
        }
    }

}
