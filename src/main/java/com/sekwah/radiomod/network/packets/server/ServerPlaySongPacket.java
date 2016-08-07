package com.sekwah.radiomod.network.packets.server;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.RadioBlock;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.network.packets.client.ClientUpdateComputerPacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerPlaySongPacket implements IMessage {

	public boolean sendToAll;

    int xCoord;
    int yCoord;
    int zCoord;

    public ServerPlaySongPacket() { }

    public ServerPlaySongPacket(boolean sendToAll, BlockPos pos) {
    	this.sendToAll = sendToAll;
        this.xCoord = pos.getX();
        this.yCoord = pos.getY();
        this.zCoord = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	NBTTagCompound tag = ByteBufUtils.readTag(buf);
    	this.sendToAll = tag.getBoolean("sendToAll");
    	this.xCoord = tag.getInteger("xCoord");
    	this.yCoord = tag.getInteger("yCoord");
    	this.zCoord = tag.getInteger("zCoord");
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	NBTTagCompound tag = new NBTTagCompound();
    	tag.setBoolean("sendToAll", this.sendToAll);
    	tag.setInteger("xCoord", this.xCoord);
    	tag.setInteger("yCoord", this.yCoord);
    	tag.setInteger("zCoord", this.zCoord);
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class Handler implements IMessageHandler<ServerShutdownComputerPacket, IMessage> {

        @Override
        public IMessage onMessage(ServerShutdownComputerPacket message, MessageContext ctx) {
        	TileEntityRadio tileEntity = (TileEntityRadio) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(new BlockPos(message.xCoord, message.yCoord, message.zCoord));
        	if(tileEntity != null) {
        		tileEntity.shutdown();

        		if(message.sendToAll) {
            		RadioMod.packetNetwork.sendToAll(new ClientUpdateComputerPacket(tileEntity.getPos(), RadioBlock.RUNSTATE_OFF));
            	}
        	}

            return null; // no response in this case
        }
    }
}