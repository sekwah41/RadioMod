package com.sekwah.radiomod.blocks.tileentities;

import com.sekwah.radiomod.blocks.RadioBlock;
import com.sekwah.radiomod.music.MusicSource;
import com.sun.istack.internal.Nullable;
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

    public TileEntityRadio(){
        this.musicSource = new MusicSource();
    }

    @Override
    public void update() {

    }

    @Nullable
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
}