package com.sekwah.radiomod.music.song;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public class SongSoundCloud extends Song {

    public String songURL = null;

    public SongSoundCloud(int idIn, String authorIn, String titleIn, String songURL) {
        super(idIn, authorIn, titleIn);
        this.songURL = songURL;
    }
}
