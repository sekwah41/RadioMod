package com.sekwah.radiomod.network.packets.client;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.music.MusicSource;
import com.sekwah.radiomod.music.song.TrackingData;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientLoadPlayerPacket implements IMessage {

    public TrackingData trackingData;
    public String uuid;

    public ClientLoadPlayerPacket(){}

    public ClientLoadPlayerPacket(String uuid, TrackingData trackingData) {
        this.uuid = uuid;
        this.trackingData = trackingData;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound tag = ByteBufUtils.readTag(buf);
        this.uuid = tag.getString("uuid");
        this.trackingData = new TrackingData(tag.getInteger("Type"), tag.getString("Source"), tag.getInteger("CurrentTick"));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("uuid", this.uuid);
        tag.setInteger("Type", this.trackingData.type);
        tag.setString("Source", this.trackingData.source);
        tag.setInteger("CurrentTick", this.trackingData.currentTick);
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class Handler implements IMessageHandler<ClientLoadPlayerPacket, IMessage> {

        @Override
        public IMessage onMessage(ClientLoadPlayerPacket message, MessageContext ctx) {
            System.out.println("Data recieved");
            RadioMod.instance.musicManager.createMusicSource(message.uuid);

            MusicSource musicManager = RadioMod.instance.musicManager.radioSources.get(message.uuid);
            if(musicManager != null && !musicManager.getIsPlaying()){
                musicManager.startFromTrackData(message.trackingData, true);
            }

            return null; // no response in this case
        }
    }
}