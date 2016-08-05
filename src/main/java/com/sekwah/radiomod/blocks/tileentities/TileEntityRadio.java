package com.sekwah.radiomod.blocks.tileentities;

import com.sekwah.radiomod.blocks.TestRadio;
import com.sekwah.radiomod.music.MusicSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public class TileEntityRadio extends TileEntity implements ITickable {

    public final MusicSource musicSource;

    public TileEntityRadio(){
        this.musicSource = new MusicSource();
    }

    @Override
    public void update() {

    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        /**
         * Check chest for largs nbt writing {@link net.minecraft.tileentity.TileEntityChest}
         * Need to start using links more. They are pretty useful :D
         */

        return compound;
    }

    public int getRunState() {
    	return TestRadio.RUNSTATE_BOOTINGUP;
    }
}