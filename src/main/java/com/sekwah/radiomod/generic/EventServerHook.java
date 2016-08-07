package com.sekwah.radiomod.generic;

import com.sekwah.radiomod.music.MobileManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
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

    @SubscribeEvent
    public void handleConstruction(EntityConstructing event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityDataManager dm = event.getEntity().getDataManager();

            dm.register(MobileManager.PARAMETER_LOCALSTATE, MobileManager.MOBILESTATE_OFF);
        }
    }
}
