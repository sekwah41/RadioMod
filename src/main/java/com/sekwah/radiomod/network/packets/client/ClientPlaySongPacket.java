package com.sekwah.radiomod.network.packets.client;

import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;

import com.sekwah.radiomod.music.song.TrackingData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientPlaySongPacket implements IMessage {

    public TrackingData trackingData;
    public String uuid;

    public ClientPlaySongPacket(){}

    public ClientPlaySongPacket(String uuid, TrackingData trackingData) {
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

    public static class Handler implements IMessageHandler<ClientPlaySongPacket, IMessage> {

        @Override
        public IMessage onMessage(ClientPlaySongPacket message, MessageContext ctx) {

            return null; // no response in this case
        }
    }
}