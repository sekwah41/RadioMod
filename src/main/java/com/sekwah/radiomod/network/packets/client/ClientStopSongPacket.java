package com.sekwah.radiomod.network.packets.client;

import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientStopSongPacket implements IMessage {

    public String uuid;

    public ClientStopSongPacket(String uuid) {
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

    public static class Handler implements IMessageHandler<ClientUpdateComputerPacket, IMessage> {

        @Override
        public IMessage onMessage(ClientUpdateComputerPacket message, MessageContext ctx) {
        	TileEntityRadio tileEntity = (TileEntityRadio) Minecraft.getMinecraft().theWorld.getTileEntity(new BlockPos(message.xCoord, message.yCoord, message.zCoord));
        	if(tileEntity != null) {
        		tileEntity.setRunState(message.runState);
        	}else{
        		System.out.println("ERROR DUUUUUUUDE!!!!!");
        	}
            return null; // no response in this case
        }
    }
}