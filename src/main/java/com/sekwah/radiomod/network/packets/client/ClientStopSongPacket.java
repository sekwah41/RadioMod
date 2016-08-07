package com.sekwah.radiomod.network.packets.client;

import com.sekwah.radiomod.network.packets.ClientPacketHandler;
import com.sekwah.radiomod.network.packets.RadioMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public class ClientStopSongPacket implements IMessageHandler<RadioMessage, IMessage> {

    /**
     *
     * @param message
     * @param ctx
     * @return Optional return message
     */
    @Override
    public IMessage onMessage(RadioMessage message, MessageContext ctx) {
        ClientPacketHandler.handleSongStartPacket(message.packet);
        return null;
    }
}