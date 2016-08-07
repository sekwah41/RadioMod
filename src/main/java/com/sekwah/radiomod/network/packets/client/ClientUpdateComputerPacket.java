package com.sekwah.radiomod.network.packets.client;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientUpdateComputerPacket implements IMessage {
    
    int xCoord;
    int yCoord;
    int zCoord;
    int runState;
    
    public ClientUpdateComputerPacket() { }

    public ClientUpdateComputerPacket(BlockPos pos, int runState) {
        this.xCoord = pos.getX();
        this.yCoord = pos.getY();
        this.zCoord = pos.getZ();
        this.runState = runState;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	NBTTagCompound tag = ByteBufUtils.readTag(buf);
    	this.xCoord = tag.getInteger("xCoord");
    	this.yCoord = tag.getInteger("yCoord");
    	this.zCoord = tag.getInteger("zCoord");
    	this.runState = tag.getInteger("RunState");
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	NBTTagCompound tag = new NBTTagCompound();
    	tag.setInteger("xCoord", this.xCoord);
    	tag.setInteger("yCoord", this.yCoord);
    	tag.setInteger("zCoord", this.zCoord);
    	tag.setInteger("RunState", this.runState);
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class Handler implements IMessageHandler<ClientUpdateComputerPacket, IMessage> {
        
        @Override
        public IMessage onMessage(ClientUpdateComputerPacket message, MessageContext ctx) {
        	TileEntityRadio tileEntity = (TileEntityRadio) Minecraft.getMinecraft().theWorld.getTileEntity(new BlockPos(message.xCoord, message.yCoord, message.zCoord));
        	if(tileEntity != null) {
        		tileEntity.setRunState(message.runState);
        	}else{
                RadioMod.logger.info("ERROR DUUUUUUUDE!!!!!");
        	}
            return null; // no response in this case
        }
    }
}