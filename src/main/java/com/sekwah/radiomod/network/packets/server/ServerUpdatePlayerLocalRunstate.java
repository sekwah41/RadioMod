package com.sekwah.radiomod.network.packets.server;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.music.MobileManager;
import com.sekwah.radiomod.music.song.TrackingData;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerUpdatePlayerLocalRunstate implements IMessage {
	int state;
	
    public ServerUpdatePlayerLocalRunstate() {
    	
    }
    
    public ServerUpdatePlayerLocalRunstate(int stateIn) {
    	this.state = stateIn;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	NBTTagCompound tag = ByteBufUtils.readTag(buf);
    	this.state = tag.getInteger("State");
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("State", this.state);
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class Handler implements IMessageHandler<ServerUpdatePlayerLocalRunstate, IMessage> {

        @Override
        public IMessage onMessage(ServerUpdatePlayerLocalRunstate message, MessageContext ctx) {
        	if(ctx.getServerHandler().playerEntity != null) {
        		ctx.getServerHandler().playerEntity.getDataManager().set(MobileManager.PARAMETER_LOCALSTATE, message.state);
        	}
            
            return null; // no response in this case
        }
    }
}