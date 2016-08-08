package com.sekwah.radiomod.blocks.tileentities;

import java.util.UUID;

import com.sekwah.radiomod.RadioMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityAddon extends TileEntity implements ITickable {
	private String ownerUUID = null;
	
	@Override
    public void update() {
    }

	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.ownerUUID = compound.getString("OwnerUUID");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setString("OwnerUUID", this.ownerUUID);
        return compound;
    }
    
    public void setOwner(String ownerUUIDIn) {
    	this.ownerUUID = ownerUUIDIn;
    	this.markDirty();
    }
    
    public String getOwnerUUID(){
        return this.ownerUUID;
    }
    
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }
    
    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }
}
