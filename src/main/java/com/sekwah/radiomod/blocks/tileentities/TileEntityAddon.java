package com.sekwah.radiomod.blocks.tileentities;

import java.util.UUID;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.RadioBlock;
import com.sekwah.radiomod.music.MusicSource;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by on 05/08/2016.
 *
 * Tile entities update when users are in a radius of about 200 blocks.
 *
 * @author GoblinBob
 */
public class TileEntityAddon extends TileEntity implements ITickable {
    private String ownerUUID = null;

    private boolean isSetup = false;
    
    public TileEntityAddon(){
    }

    @Override
    public void update() {
    }
    
    public void setOwner(String ownerUUIDIn) {
    	this.ownerUUID = ownerUUIDIn;
    	this.markDirty();
    }

    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound nbttagcompound = this.writeToNBT(new NBTTagCompound());
        return nbttagcompound;
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

    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }

    public String getOwnerUUID(){
        return this.ownerUUID;
    }

    public void onChunkUnload()
    {
    }

    public void onLoad()
    {
    }
}