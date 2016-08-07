package com.sekwah.radiomod.music.song;

import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;

/**
 * Created by on 07/08/2016.
 *
 * @author sekwah41
 */
public class TimingData {

    public final int frames;
    public final float ms_per_frame;

    public TimingData(float ms_per_frame, int frames){
        this.ms_per_frame = ms_per_frame;
        this.frames = frames;
    }
}
