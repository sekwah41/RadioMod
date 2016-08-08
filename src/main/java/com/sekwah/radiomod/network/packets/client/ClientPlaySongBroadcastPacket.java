package com.sekwah.radiomod.network.packets.client;

import com.sekwah.radiomod.music.song.TrackingData;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientPlaySongBroadcastPacket implements IMessage {

    public TrackingData trackingData;
    public String uuid;

    public ClientPlaySongBroadcastPacket(){}

    public ClientPlaySongBroadcastPacket(String uuid, TrackingData trackingData) {
        this.uuid = uuid;
        this.trackingData = trackingData;
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

    public static class Handler implements IMessageHandler<ClientPlaySongBroadcastPacket, IMessage> {

        @Override
        public IMessage onMessage(ClientPlaySongBroadcastPacket message, MessageContext ctx) {

            return null; // no response in this case
        }
    }
}