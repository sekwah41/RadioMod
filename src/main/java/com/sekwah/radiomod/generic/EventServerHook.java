package com.sekwah.radiomod.generic;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.music.MobileManager;

import com.sekwah.radiomod.network.packets.client.ClientConfigPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
        RadioMod.instance.musicTracker.updateTracks();
    }

    @SubscribeEvent
    public void userLoggedIn(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
            RadioMod.logger.info(player);
            RadioMod.packetNetwork.sendTo(new ClientConfigPacket(RadioMod.proxy.settings.soundRadius, RadioMod.proxy.settings.soundDropoff), player);
        }
    }

    @SubscribeEvent
    public void handleConstruction(EntityConstructing event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityDataManager dm = event.getEntity().getDataManager();

            dm.register(MobileManager.PARAMETER_LOCALSTATE, MobileManager.MOBILESTATE_OFF);
        }
    }
}
