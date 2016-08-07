package com.sekwah.radiomod.music;

import com.sekwah.radiomod.RadioMod;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;

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

    public void updateSources() {
        float recordVol = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS);
        float masterVol = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER);

        for(String uuid : radioSources.keySet()){

            if(sourceDistances.containsKey(uuid)){
                float distance = sourceDistances.get(uuid);
                float volume = (float) (1.1f - (Math.sqrt(distance) / 64f));
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
