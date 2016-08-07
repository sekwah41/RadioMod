package com.sekwah.radiomod.music;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.music.song.TimingData;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

    public TimingData getTimingData(InputStream inputStream){
        Bitstream bitstream = new Bitstream(inputStream);
        try {
            Header head = bitstream.readFrame();
            int max_frames = head.max_number_of_frames(inputStream.available());
            inputStream.close();
            new TimingData(head.ms_per_frame(), max_frames);
        } catch (BitstreamException | IOException e) {
            RadioMod.logger.info("Error getting timing data.");
            e.printStackTrace();
        }
        return null;
    }

    public void updateSources() {
        float recordVol = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS);
        float masterVol = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER);

        for(String uuid : radioSources.keySet()){

            if(sourceDistances.containsKey(uuid)){
                try{
                    float distance = sourceDistances.get(uuid);
                    float volume = 1f;
                    if(distance > RadioMod.proxy.settings.soundRadius){
                        volume = (float) (1f - (Math.sqrt(distance) / RadioMod.proxy.settings.soundDropoff));;
                    }
                    //RadioMod.logger.info(volume);
                    if(volume > 1f){
                        volume = 1f;
                    }
                    else if(volume < 0f){
                        volume = 0;
                    }
                    /**
                     * This value is to make it so the max volume is about the volume of the music discs
                     */
                    volume *= 0.4;
                    volume *= recordVol;
                    volume *= masterVol;
                    radioSources.get(uuid).setVolume(volume);
                }
                catch(NullPointerException e){

                }

            }
            else{
                MusicSource source = radioSources.get(uuid);
                if(source.getIsPlaying()){
                    RadioMod.logger.info(uuid + ": stopped");
                    source.stopMusic();
                }
            }
        }
    }
}
