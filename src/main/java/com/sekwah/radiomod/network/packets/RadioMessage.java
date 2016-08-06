package com.sekwah.radiomod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public class RadioMessage implements IMessage {

    public byte[] packet;
    public int packetLength;

    public RadioMessage(){
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.packetLength = buf.readInt();
        this.packet = buf.readBytes(this.packetLength).array();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.packet.length);
        buf.writeBytes(this.packet);
    }

}
