package com.sekwah.radiomod.network.packets.client;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.RadioSettings;
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

public class ClientConfigPacket implements IMessage {

    private double soundRadius;
    private double soundDropoff;

    public ClientConfigPacket(double soundRadius, double soundDropoff) {
        this.soundRadius = soundRadius;
        this.soundDropoff = soundDropoff;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	NBTTagCompound tag = ByteBufUtils.readTag(buf);
    	this.soundRadius = tag.getDouble("soundRadius");
    	this.soundDropoff = tag.getDouble("soundDropoff");
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	NBTTagCompound tag = new NBTTagCompound();
    	tag.setDouble("soundRadius", this.soundRadius);
    	tag.setDouble("soundDropoff", this.soundDropoff);
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class Handler implements IMessageHandler<ClientConfigPacket, IMessage> {

        @Override
        public IMessage onMessage(ClientConfigPacket message, MessageContext ctx) {
            RadioMod.proxy.settings.soundRadius = message.soundRadius;
            RadioMod.proxy.settings.soundDropoff = message.soundDropoff;
            return null; // no response in this case
        }
    }
}