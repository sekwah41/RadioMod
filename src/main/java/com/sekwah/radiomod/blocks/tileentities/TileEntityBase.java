package com.sekwah.radiomod.blocks.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class TileEntityBase extends TileEntity implements ITickable {


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


}