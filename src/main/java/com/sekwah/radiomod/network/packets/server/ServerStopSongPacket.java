package com.sekwah.radiomod.network.packets.server;

import com.sekwah.radiomod.RadioMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerStopSongPacket implements IMessage {

	public String uuid;

	public ServerStopSongPacket() {
    }
	
    public ServerStopSongPacket(String uuid) {
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

    public static class Handler implements IMessageHandler<ServerStopSongPacket, IMessage> {

        @Override
        public IMessage onMessage(ServerStopSongPacket message, MessageContext ctx) {
            RadioMod.instance.musicTracker.stopSong(message.uuid);
            return null; // no response in this case
        }
    }
}