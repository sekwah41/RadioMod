package com.sekwah.radiomod.network.packets.server;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.music.song.TrackingData;
import com.sekwah.radiomod.network.packets.client.ClientLoadPlayerPacket;
import com.sekwah.radiomod.network.packets.client.ClientUpdateComputerPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerLoadPlayerPacket implements IMessage {

    public String uuid;

    public ServerLoadPlayerPacket() {

    }

    public ServerLoadPlayerPacket(String uuid) {
    	this.uuid = uuid;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	NBTTagCompound tag = ByteBufUtils.readTag(buf);
    	this.uuid = tag.getString("uuid");
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	NBTTagCompound tag = new NBTTagCompound();
    	tag.setString("uuid", this.uuid);
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class Handler implements IMessageHandler<ServerLoadPlayerPacket, IMessage> {

        @Override
        public IMessage onMessage(ServerLoadPlayerPacket message, MessageContext ctx) {
            RadioMod.logger.info("Reload packet");
            synchronized(RadioMod.instance.musicTracker.sync){
                if(RadioMod.instance.musicTracker.trackingMap.containsKey(message.uuid)){
                    TrackingData data = RadioMod.instance.musicTracker.trackingMap.get(message.uuid);
                    RadioMod.logger.info(data.currentTick);
                    RadioMod.packetNetwork.sendTo(new ClientLoadPlayerPacket(message.uuid, data), ctx.getServerHandler().playerEntity);
                }
            }

            return null; // no response in this case
        }
    }
}