package com.sekwah.radiomod.network.packets.server;

import com.sekwah.radiomod.network.packets.RadioMessage;
import com.sekwah.radiomod.network.packets.ServerPacketHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public class ServerPlaySongPacket extends RadioMessage implements IMessageHandler<ServerPlaySongPacket, IMessage> {

    public ServerPlaySongPacket(byte[] payload) {
        this.packet = payload;
        this.packetLength = payload.length;
    }

    public IMessage onMessage(ServerPlaySongPacket message, MessageContext ctx) {
        ServerPacketHandler.handleSongStartPacket(message.packet);
        return null;
    }
}