package com.sekwah.radiomod.network.packets.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientStopSongBroadcastPacket implements IMessage {

    public String uuid;

    public ClientStopSongBroadcastPacket(){}

    public ClientStopSongBroadcastPacket(String uuid) {
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

    public static class Handler implements IMessageHandler<ClientStopSongBroadcastPacket, IMessage> {

        @Override
        public IMessage onMessage(ClientStopSongBroadcastPacket message, MessageContext ctx) {

            return null; // no response in this case
        }
    }
}