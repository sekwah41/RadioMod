package com.sekwah.radiomod.client;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.music.MusicManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by on 06/08/2016.
 *
 * @author sekwah41
 */
public class EventHook {

    @SubscribeEvent
    public void worldUnload(WorldEvent.Unload world)
    {
        RadioMod.logger.info("Stopping all radio's");
        RadioMod.instance.musicManager.stopAllPlayers();
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {

    }

}
