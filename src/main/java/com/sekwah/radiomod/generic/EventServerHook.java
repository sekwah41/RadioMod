package com.sekwah.radiomod.generic;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by on 06/08/2016.
 *
 * @author sekwah41
 */
public class EventServerHook {

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {

    }

    @SubscribeEvent
    public void userLoggedIn(PlayerEvent.PlayerLoggedInEvent event){

    }


}
