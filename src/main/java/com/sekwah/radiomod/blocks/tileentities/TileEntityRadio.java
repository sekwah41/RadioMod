package com.sekwah.radiomod.blocks.tileentities;

import com.sekwah.radiomod.blocks.RadioBlock;
import com.sekwah.radiomod.music.MusicSource;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public class TileEntityRadio extends TileEntity implements ITickable {

    public final MusicSource musicSource;

    private int rotation = 0;
    private int runState = RadioBlock.RUNSTATE_OFF;
    private int bootupSequence = 0;
    
    public TileEntityRadio(){
        this.musicSource = new MusicSource(this);
    }

    @Override
    public void update() {
    	switch(this.getRunState()) {
    		case RadioBlock.RUNSTATE_BOOTINGUP:
    			if(this.bootupSequence > 0)this.bootupSequence--;
    			else this.setRunState(RadioBlock.RUNSTATE_ON);
    		break;
    	}
    }

    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 4, this.getUpdateTag());
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.rotation = compound.getByte("Rot");
        this.runState = compound.getInteger("RunState");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setByte("Rot", (byte)(this.rotation & 255));
        compound.setInteger("RunState", this.runState);
        /**
         * Check chest for largs nbt writing {@link net.minecraft.tileentity.TileEntityChest}
         * Need to start using links more. They are pretty useful :D
         */

        return compound;
    }

    public int getRunState() {
    	return this.runState;
    }

    @SideOnly(Side.CLIENT)
    public int getRotation()
    {
        return this.rotation;
    }

    public void setRotation(int rotation)
    {
        this.rotation = rotation;
    }
    
    public void bootUp() {
    	this.runState = RadioBlock.RUNSTATE_BOOTINGUP;
    	System.out.println("BOOTING UPPPPP!!!!!");
    	this.bootupSequence = 50;
    }
    
    public void shutdown() {
		this.runState = RadioBlock.RUNSTATE_OFF;
	}
    
    public void setRunState(int runStateIn) {
    	if(runStateIn == RadioBlock.RUNSTATE_BOOTINGUP && this.runState != RadioBlock.RUNSTATE_BOOTINGUP){
    		this.bootUp();
    	}
    	this.runState = runStateIn;
    }
}