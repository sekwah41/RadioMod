package com.sekwah.radiomod.client;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.music.MusicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by on 06/08/2016.
 *
 * TODO when other music is started it seems to lose track for some reason .-.
 *
 * @author sekwah41
 */
public class EventHook {

    @SubscribeEvent
    public void worldUnload(WorldEvent.Unload world)
    {
        synchronized (RadioMod.instance.musicManager.sync) {
            RadioMod.logger.info("Stopping all radio's");
            RadioMod.instance.musicManager.stopAllPlayers();
            RadioMod.instance.musicManager.radioSources.clear();
            RadioMod.instance.musicManager.sourceDistances.clear();
        }
        synchronized (RadioMod.instance.musicTracker.sync) {
            RadioMod.instance.musicTracker.trackingMap.clear();
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        //RadioMod.logger.info(FMLClientHandler.instance().getWorldClient());
        if(FMLClientHandler.instance().getWorldClient() != null && !Minecraft.getMinecraft().isGamePaused()){
            if(event.phase == TickEvent.Phase.START){
                RadioMod.instance.musicManager.sourceDistances.clear();
            }
            else{
                //RadioMod.logger.info(event.phase);
                RadioMod.instance.musicManager.updateSources();
            }
        }
    }

}
